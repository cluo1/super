#!/bin/bash -l
###############################################################################
# 功能:   兴业理财代销 后端服务包监控程序
# 描述:   1.需要配置为定时任务，监测到服务包时，停止服务，拷贝包，并自动部署
#         	目前部署为单机监控，每个服务器上添加一个定时任务，定时执行这个脚本。
#         	后续扩展为集中式脚本。
#         2.如果不配置为定时任务，可以集成到jenkins中远程调用此脚本。
#         3.不配置环境变量。 在停止和启动脚本中配置要启动的包和环境变量。
###############################################################################

###############################################################################
# 主程序逻辑: 一、监控包上传目录
#			  二、如果有新包
#             三、停止已有服务
#             四、启动新服务
# 存在问题:   一、存到上传目录里的包，如果已存在，就停止，然后备份、拷贝、启动
#             二、如果不存在，就直接拷贝启动。
#             三、如果要强制重启所有包，直接调用重启脚本。
#			  四、暂时不考虑一个包在同一个节点启动多次的问题。
#             五、启动端口在启动时判断自动递增（关键端口由启动脚本指定）
###############################################################################

###############################################################################
# 定义系统变量

# 包上传目录(实际包上传的位置)
UPLOAD_DIR="/home/iwss/upload"
BACKUP_DIR="/home/iwss/backup"
APP_DIR="/home/iwss/app/iwss"
SCRIPT_DIR="/home/iwss/shbin"


EUREKA_SERVERS="192.168.90.141"

# 需要使用一个全局变量，接收split_service_name的返回值（没找到怎么返回字符串:)）
SPLIT_SERVICE_NAME=""
###############################################################################
# 定义函数

# 拆分字符串，并返回第一个字符串，用于取服务名
# 参数1 要拆分的字符串
# 参数2 分隔符
# 返回 第一个元素
function split_service_name()
{
	# 保存旧的 分隔符
	OLD_IFS="$IFS"
	# 设置新的分隔符        
	IFS=${2}
	
	#拆分字符串
	SPLIT_STRS=($1)
	
	# 恢复旧的分隔符
	IFS="$OLOD_IFS"
	
	# 返回字符串	
	echo $SPLIT_STRS
	SPLIT_SERVICE_NAME=$SPLIT_STRS
	
}





###############################################################################
# 程序主逻辑


# 参数预处理区
# 处理一下命令行参数，并赋值到参数变量中。  添加参数需要同样的格式。
# 参数 -p|--projects   要启动的项目，使用空格分隔
# 参数 -s|--eurekaservers   指定 eurekaservers（ip） 用空格分隔

GETOPT_ARGS=`getopt -o p:s: -al projects:,eurekaservers: -- "$@" `
eval  set -- "$GETOPT_ARGS"

while true ; do
        case "$1"  in                
				-p|--projects) PROJECTS="$2" ; shift 2 ;;
				-s|--eurekaservers) EUREKA_SERVERS="$2" ; shift 2 ;;				
                --) shift; break ;;
                #*)  show_usage exit 1 ;
        esac
done




# 判断确认主目录存在
if [ ! -d "${UPLOAD_DIR}" ] ; then
	echo "主目录不存在，请先确认主目录正确！"
	exit 1;
fi

# 判断上传文件
# 使用ls命令，通过grep返回包文件结果（因为ls *.jar 在无包时，返回结果判断有问题）
cd ${UPLOAD_DIR}
UPLOAD_PACKAGE_STR=$( ls  | grep .*\.jar)  # 注意正则是 .*\.jar 

echo $UPLOAD_PACKAGE_STR

# 如果为空则退出
if [ -z "$UPLOAD_PACKAGE_STR" ]; then 
    echo "UPLOAD_PACKAGE 没有可更新的包，退出!"
    exit 1; 
fi


# 拆分服务为数组
UPLOAD_PACKAGES=(${UPLOAD_PACKAGE_STR})

echo "包列表${UPLOAD_PACKAGES} 这是所有包列表"
# echo "包列表数组：${UPLOAD_PACKAGES[@]}"
# 停止本机部署的服务。调用停止服务脚本。
# 看stopService.sh 支持什么样的参数，决定是循环停止或是单个停止。 结果是一样的。
# stopService.sh
# 只停止要更新的包
for service in ${UPLOAD_PACKAGES[@]}
do
	echo " 要处理的服务 ${service} 。。。"
	# 取到服务名（使用全局变量拆分字符串。。。）
	SPLIT_SERVICE_NAME=""	
	split_service_name $service "-"
	service_name=$SPLIT_SERVICE_NAME
	echo "拆分后的服务名为 ${service_name}"	

	# 停止服务（有可能服务不存在）
	echo "正在停止服务 ${sevice_name}"
	${SCRIPT_DIR}/stopCloudNode.sh -p $service_name
	
	
	# 备份包到备份目录
	# 只备份更新的包  (可能版本不一致，所以用服务名)
	# 有可能源文件不存在（不影响，后续添加先判断文件是否存在）
	echo "正在备份${service_name}"
	mv ${APP_DIR}/${service_name}* ${BACKUP_DIR}/

	
	# 移动新包到包目录（按文件名移动）
	echo "正在移动${service}"	
	mv   ${UPLOAD_DIR}/${service}     ${APP_DIR}/

	# 启动服务
	# 只启动更新的服务（按服务名启动）  (指定eureka_servers)
	echo "正在启动服务 $service_name"
	${SCRIPT_DIR}/startCloudNode.sh -p $service_name   -s $EUREKA_SERVERS
	

done

# su - finance
#mkdir upload
#mkdir shbin
#mkdir package
#mkdir backup
#mkdir app/finance -p







