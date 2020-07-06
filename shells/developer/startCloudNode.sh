#!/bin/bash -l
###############################################################################
# 功能:    综合财富平台云架构标准程序 节点服务启动程序
# 描述:   1.启动服务。
###############################################################################

###############################################################################
#  程序大纲:
#	一、 配置部分
#	二、 函数部分
#	三、正式逻辑
#
#  程序逻辑：
#    一、参数校验与转换
#    二、判断待启动的服务（所有服务【仅发布包里的】、指定服务）
#    三、启动服务
###############################################################################

###############################################################################
#  配置部分
APP_DIR="/home/iwss/app/iwss"
TEST_LOG_DIR="/home/iwss/testlog"
JOYIN_LOG_DIR="/home/iwss/logs"

#定义 所有要启动的业务项目 
#SERVICEARRAY=("iwsseurekaserver" "iwssconfigserver" "iwssgateway" "iwssservicetran" "iwssbatchta" "sysman" "iwssservicemngr" "iwssservicequota")
SERVICEARRAY=("iwsseurekaserver" "iwssconfigserver" "iwssgateway" "sysman" "transman" "standardif" "retailif" "adaptif" "productman")

#SERVICE_NAME=""

#定义一个map ，用于存放每个服务部署的细节信息
declare -A servicemap;

#定义每个服务的部署参数（按顺序）
# 参数说明 ：
# 		一、内存min 和max 
#		二、端口
#		三、考虑后续添加。。。
#要将每个服务列举全面

# 系统服务
servicemap["iwsseurekaserver"]="128m 256m 8100"  
servicemap["iwssconfigserver"]="128m 256m 8200"  
servicemap["iwssgateway"]="128m 512m 8300"  

# 业务服务
#servicemap["iwssservicetran"]="512m 2048m 9100"  
#servicemap["iwssbatchta"]="512m 2048m 9800"  
#servicemap["sysman"]="512m 2048m 9910"  
#servicemap["iwssservicemngr"]="512m 2048m 9900"  
#servicemap["iwssservicequota"]="512m 2048m 9300"  
#servicemap["fmgservice"]="512m 2048m 8216"  
#servicemap["fweservice"]="512m 2048m 8217"  
#servicemap["fwxpreweb"]="512m 2048m 8218"  

servicemap["sysman"]="512m 2048m 8216"  
servicemap["transman"]="512m 2048m 8217"  
servicemap["standardif"]="512m 2048m 8218"  
servicemap["retailif"]="512m 2048m 8219"  
servicemap["adaptif"]="512m 2048m 8220"  
servicemap["productman"]="512m 2048m 8221"  


# 服务参数配置

# 使用的配置文件。  'dev' 'test' 'prod'   目录使用这三个开发、测试 、生产
ACTIVE_PROFILE='dev'

# 定义参数
# eureka.client.serviceUrl.defaultZone      ,服务地址可以配多个，逗号分隔
EUREKA_USERNAME='joyintech'
EUREKA_PASSWORD='Joyintech201911'
EUREKA_SERVERS='192.168.90.141'
EUREKA_SERVERPORT=8100

# 数据库连接参数
DATASOURCE_URL="jdbc:oracle:thin:@192.168.90.237:1521/orcl.zhang"
DATASOURCE_USER="xingyepoc"
DATASOURCE_PASS="xingyepoc"
DRIVER_CLASS="oracle.jdbc.driver.OracleDriver"
# 待替换（这个加密密码是原来的）
#DATASOURCE_PASS_DRUID="DElmZcAA+roBiYXx9+A+s0+B7jvShhwEcKpqXoj30ofEwYI8la8Pwk0wEEksw9cvj+YO3tc2uQ3fVpDzEIsVfA=="
#JOYIN_DRUID_PUBLIC_KEY="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALuAGvcch7eQGGikzNsf+HZZ8OtymPINyrwUWxOgQ8R5A71A4f/AlCqwprOo/aNMporUN4o+qwPTV7iLcpmrFkkCAwEAAQ=="


#本机端口,用于日志里面

#SERVICE_ID=`cat /home/finance/serviceId`
# 注意要获取本地网段 （更换环境时要注意网段）
SERVICE_IDSTR=`ip addr | grep "192.168" | awk '{ print $2}'`
SERVICE_IDS=($SERVICE_IDSTR)
SERVICE_ID=${SERVICE_IDS[0]}  # 取第一个IP
LOG_HOST=${SERVICE_ID%/*}  # 截掉后边的/24


# 配置部分结束
###############################################################################


###############################################################################
#  函数部分

# 启动服务
# 参数: 参数1 服务名
# 参数：参数2 配置项
function func_start_service()
{
	
	tobestart=$1
	echo "正在启动服务 $1 "
	# 使用$* 取得所有参数 。 使用shift 1 将第1个参数截取掉。
	# java -Dxxx.xxx  -jar filename  -D 参数必须在-jar 参数前
	shift 1
	java $* -jar  $tobestart >/home/iwss/${SERVICE_FILE_NAME}.log 2>&1 &
	#java -jar $*
	
	echo  "已完成启动命令调用，服务  $tobestart "
	
}



#  函数部分结束
###############################################################################


###############################################################################
# 脚本正式逻辑
# 逻辑描述: 一、启动eurekaserver  (如果不在本机，不需要判断）
#			二、启动configserver (需要eurekaserver 启动成功）
#			三、启动gateway      (可以不需要判断和等待）
#			四、启动其他节点
# 注意事项:
#           一、需要添加判断eurekaserver是否已启动。
#			二、注册中心、配置中心、网关端口固定 。
#			三、其他服务端口使用递增（不使用数据库中的配置端口），从
#
###############################


# 参数预处理区
# 处理一下命令行参数，并赋值到参数变量中。  添加参数需要同样的格式。
# 参数 -p|--projects   要启动的项目，使用空格分隔
# 参数 -s|--eurekaservers   指定 eurekaservers（ip） 用空格分隔
# 参数 -l|--local-ip        指定本机IP,用于日志

GETOPT_ARGS=`getopt -o p:s:l: -al projects:,eurekaservers:,local-ip: -- "$@" `
eval  set -- "$GETOPT_ARGS"

while true ; do
        case "$1"  in                
				-p|--projects) PROJECTS="$2" ; shift 2 ;;
				-s|--eurekaservers) EUREKA_SERVERS="$2" ; shift 2 ;;
				-l|--local-ip) LOG_HOST="$2" ;  shift 2 ;;
                --) shift; break ;;
                #*)  show_usage exit 1 ;
        esac
done



## 组合参数部分
EUREKA_URL=""
for url in ${EUREKA_SERVERS}
do
	EUREKA_URL="${EUREKA_URL}http://${EUREKA_USERNAME}:${EUREKA_PASSWORD}@${url}:${EUREKA_SERVERPORT}/eureka,"
	
done
#删除最后一个字符
EUREKA_URL=${EUREKA_URL%?}




# 要启动的项目
SERVICETODO=""
# 处理要启动的服务
# 基本逻辑： 不指定服务的情况下，按服务包里jar包列表启动。
# 先处理要启动的列表
if [ ${PROJECTS}"x" == "allx" -o ${PROJECTS}"x" == "x" ] ; then
	# 循环构建后台服务 并启动 如果命令行没有参数，就把服务列表放进去
		
	SERVICETODO=${SERVICEARRAY[@]}
else
	#拆分成数组
	SERVICETODO=(${PROJECTS})
fi



# 正式启动项目
for service in ${SERVICETODO[@]}
do	

		# 如果服务包存在，则启动
		findservice=`ls ${APP_DIR} | grep ${service} | wc -l`
		if [ ! "$findservice" == "1" ] ; then 
			continue
		fi
		
		CONFIG_SERVER_PARAM=""
		# 通过map映射，取得服务的配置，并拆分成数组
		SERVICE_CONFIG=(${servicemap[${service}]})
		# 这里添加一个验证，防止没有配置导致启动出错
		if [ "${SERVICE_CONFIG}X" == "X" ] ; then
			continue
		fi
		
		
		# 添加第一个参数(参数顺序在配置中固定。 最小内存 最大内存 端口)
		CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Xms${SERVICE_CONFIG[0]} "
		CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Xmx${SERVICE_CONFIG[1]} "
		
		# 为gateway 添加配置
		#if [ "$service" == "iwsseurekaserver"  -o "$service" == "iwssconfigserver" ] ; then 
			CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Dserver.port=${SERVICE_CONFIG[2]} " 
			
			# 添加EUREKA 的配置
			CONFIG_SERVER_PARAM=" ${CONFIG_SERVER_PARAM}  -Dspring.cloud.config.profile=${ACTIVE_PROFILE} "
			CONFIG_SERVER_PARAM=" ${CONFIG_SERVER_PARAM}  -Deureka.client.serviceUrl.defaultZone=${EUREKA_URL} "
			
			# 添加日志配置
			CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Deureka.instance.instance-id=${LOG_HOST}:${SERVICE_CONFIG[2]} " 
			CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Djoyin.log.name=${service} "
			# 这样的参数可以加在bootstrap.properties 里
			CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Deureka.instance.preferIpAdress=true "
			
			
			# 为eurekaserver 添加配置
			if [ "$service" == "iwsseurekaserver"  ] ; then
				CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM}  -Dspring.security.user.name=${EUREKA_USERNAME}"
				CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Dspring.security.user.password=${EUREKA_PASSWORD}"
			fi
				
			#   为CONFIGSERVER 添加其他配置项(数据库 )
			if [ "$service" == "iwssconfigserver" ] ; then
				# 数据库配置
				CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Dspring.datasource.url=${DATASOURCE_URL} "
				CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Dspring.datasource.username=${DATASOURCE_USER} "
				CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Dspring.datasource.password=${DATASOURCE_PASS} "
				CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Dspring.datasource.driver-class-name=${DRIVER_CLASS} "
						
				
			fi
		
		#fi
		
		# 取得服务文件名
		SERVICE_FILE_NAME=`ls  ${APP_DIR}  | grep ${service}`
		
		
		# 打印启动参数(用于调试)
		echo ${CONFIG_SERVER_PARAM}		
		echo $SERVICE_FILE_NAME
		
		# 调用服务启动函数 ，启动服务
		func_start_service "${APP_DIR}/${SERVICE_FILE_NAME}" ${CONFIG_SERVER_PARAM}
		
		# 如果是 eurekaserver 或是 config server ，等30秒
		# 暂时未考虑分布式等待。。。
		if [ "$service" == "iwsseurekaserver" -o "$service" == "iwssconfigserver"  ] ; then
			sleep 30
		fi
		
done






#  正式逻辑结束
###############################################################################





