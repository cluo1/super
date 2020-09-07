#!/bin/sh
################################################################################
# 作者:   史骏马  shijunma@joyintech.com
# 时间:   2019年10月16日 09:49:48
# 功能:   综合财富平台KEYBOARD后端服务停止脚本
#         停止服务的脚本。
#         不使用参数时， 停止所有服务（测试环境可以使用kill -9)
################################################################################

# 先配置JAVA 和MAVEN 的环境变量，以确保java和mvn命令正常。（已配置为全局命令）
#source /u01/app/javamvn.sh
#java -version
#mvn -v


# 使用提示
# 使用提示
function show_usage()
{
	echo "综合财富平台微信后端服务停止脚本"
	echo  "所有参数都有默认值，也可以在命令行参数中指定。 \n "
	echo "		指定参数: \n		"
	echo "		-b 或--base-dir  定义程序主目录所在位置  默认/u01/app/finance/source \n "
	echo "		-p   定义单独停止的服务 例如 脚本 -p sysman \n "
	echo "			例 -b /u01/app 或  --base-dir=/u01/app  \n "
	
	echo "-h 或 --help 显示帮助参数信息 "
}

echo "开始构建 :\n"
date +%F" "%H:%M:%S

show_usage

######################################所有停止脚本可能修改地方###########################################
#参数定义
#每台机器不一样的配置
DIFFERENT_CONF=/data2/finance/shbin/differentConf

# 定义程序主目录
BASE_DIR=/data2/finance/app/finance

#定义全部服务都可以grep到的关键词
KEYWORD_FOR_GREP=SNAPSHOT



#定义要停止的服务
BUILD_PROJECTS=""

#定义停止的服务的jar包全名
SERVICE_FILE_NAME=""

#定义全局参数服务名
SERVICE_NAME=""

#从shbin的differentConf里面读取本机IP（ib2使用）、本机别名、本机应该启动的服务参数
source ${DIFFERENT_CONF}

#定义全部的服务,主要是顺序从这里取
ALL_SERVICE=$stop_all_server

##定义 停止的项目
SERVICE_TO_BUILD=""
SERVICE_TO_BUILD=$different_service_name

######################################所有停止脚本可能修改地方###########################################

################################################################################
# 函数定义区

# 函数，判断端口是否已经占用
function is_port_used()
{
	# 待添加判断端口占用情况，如果占用，持续加1
	#PORTUSEDCOUNT=$(  netstat -an|grep ${1}|wc -l )
	#root用户才能用-lnp!（可以获取所有用户的端口）
	PORTUSEDCOUNT=netstat -lnp|grep ${1}|wc -l
	if [ ${PORTUSEDCOUNT}>0 ] ; then
		return 1;
	else
		return 0;
	fi
}

# 根据服务名取得文件名
# 参数: 1 服务名，例如 wxpreweb
# 说明：通过ls和grep 命令，从系统运行主目录里面取得服务的文件名,
#       (剔除了"_"防止临时备份包影响)
# 不返回：只是修改了局变量SERVICE_FILE_NAME的值
function get_service_file_name()
{
    SERVICE_NAME=$1
    SERVICE_FILE_NAME_NUMBER=""
    SERVICE_FILE_NAME_NUMBER="`ls  ${BASE_DIR}  | grep ${SERVICE_NAME} |grep jar|grep -v "_" |wc -l`"
    
    SERVICE_FILE_NAME=""
    
    if [ ${SERVICE_FILE_NAME_NUMBER}"x" == "1x" ] ; then
		SERVICE_FILE_NAME="`ls  ${BASE_DIR}  | grep ${SERVICE_NAME} |grep jar|grep -v "_"`"
	else
		echo "==========================================ERROE================================================================="
		echo "The number of ${SERVICE_NAME} file is ${SERVICE_FILE_NAME_NUMBER}, is not only one;";
		echo "error ! Please check this service file in ${BASE_DIR} !" ;
		echo " ${SERVICE_NAME}服务的文件在${BASE_DIR}不存在,或者文件不止一个,请确认文件是否正确!";
		echo "(注意:脚本会根据服务名搜文件,排除带“_”的服务名)";
	fi    
}
# 根据服务名取得端口
# 参数: 1 服务名，例如 wxpreweb
# 说明：通过ps 命令，从系统进程中取得启动服务时的命令 ，命令行中包含-Dserver.port=
#       从其中取得端口号
# 返回：端口号,如果未找到-Dserver.port参数，则返回nothing
#
function get_service_port()
{
    SERVICE_NAME=""
    
    SERVICE_NAME=$1
	
	get_service_file_name $SERVICE_NAME
    
	
	# 使用PS命令取得命令行参数 （传入参数为服务名）
	COMMANDS_LINE=`ps -aux|grep ${SERVICE_FILE_NAME}|awk ' { if ($11=="java") { print  } }'`
	
	

	# 使用空格拆分命令行为数组
	COM_PARAM_ARRAY=(${COMMANDS_LINE})
	
	#echo "正在根据服务名${1}获取端口 "

	#遍历数组取得端口
	for param in ${COM_PARAM_ARRAY[@]}
	do
			include=$(echo $param | grep 'server.port')
			if [[ ${include}"X" != "X" ]]
			then
					#echo ${include}"取得的端口是"${include#*=}
					# 使用#运算符，截取=号右边的字符
					echo ${include#*=}
					return ${include#*=}			
			fi
	done

	# 如果没有找到
	return "nothing"

}

# 停止单个服务（使用端口号）
# 参数：  1 端口号
# 说明：  根据服务名取得端口，然后进行停止（测试环境暂时直接kill）
# 不指定打包使用的配置文件 ，在启动时处理配置文件。
function func_service_stop_port()
{
	
	
	# 优雅的停止服务，需要先取得端口
	port=${1}
	echo "当前正在根据端口停止服务,端口是${1}! \n"
	
	echo "curl -X POST http://localhost:${port}/shutdown  "
	# 使用curl 发送停止命令 ，如果需要密码，在参数中添加密码
	HTTPRESULT=`curl -X POST http://localhost:${port}/shutdown ` 
	
	RESULT=$(echo $HTTPRESULT | grep 'Shutting' )
	if [[ "$RESULT" != "" ]]
	then
		# 等待后台任务执行完成。 时间根据最长业务时间确定
		sleep 5
		echo "${service_name}服务已经成功关闭 "
	else
		# 关闭服务响应不正确。 可能需要再次检查端口和服务状态。。。
		echo "${service_name}关闭服务响应不正确，请检查服务是否关闭 "
		# 或者考虑强行关闭一次
		# ps -aux|grep ${1}|awk ' { if ($11=="java") { print $2 } }' |xargs kill -9
	fi
		
	


}


# 停止单个服务（使用服务名）
# 参数：  1 服务名（项目名，同目录名）   端口号根据服务名获取
# 说明：  根据服务名取得端口，然后进行停止（测试环境暂时直接kill）
# 不指定打包使用的配置文件 ，在启动时处理配置文件。
function func_service_stop()
{
	echo "当前正在根据服务名停止服务${1} "
	service_name=${1}
	# 优雅的停止服务，需要先取得端口
	#这里的port取得是get_service_port函数里面echo的值!!!
	port=`get_service_port "$service_name"`
	
	if [[ "$port" != "nothing" ]]
	then 	
		echo "取到端口是$port"
		# 取到端口的，直接按端口关闭
		func_service_stop_port  $port
	else
		echo "未取到服务端口，可能服务没有运行，或服务名错误 "
	fi
		
	


}








################################################################################
# 参数处理区
# 处理一下命令行参数，并赋值到参数变量中。  添加参数需要同样的格式。

GETOPT_ARGS=`getopt -o b:d:p:h -al base-dir:,dist-dir: -- "$@" `
eval  set -- "$GETOPT_ARGS"

while true ; do
        case "$1"  in                
				-p|--build-projects) BUILD_PROJECTS="$2" ; shift 2 ;;
				
				-l|--local_ip) LOG_HOST="$2" ;  shift 2 ;;
				
                --) shift; break ;
                #*)  show_usage exit 1 ;;
                #-h|--help) show_usage exit 1 ;
        esac
done


###############################################################################
# 处理完参数后，组合到服务的启动参数中

###############################################################################

###############################################################################
# 脚本正式逻辑
# 逻辑描述: 一、启动eurekaserver
#			二、启动configserver (需要eurekaserver 启动成功）
#			三、启动gateway      (可以不需要判断和等待）
#			四、启动其他节点
# 注意事项:
#           一、需要添加判断eurekaserver是否已启动。
#			二、注册中心、配置中心、网关端口固定 。
#			三、其他服务端口使用递增（不使用数据库中的配置端口），从
#
###############################

echo "开始停止服务..."

# 判断是否需要停止所有服务（条件:无服务参数）

# 无参数的情况下，直接停止所有服务（）
if [ ${BUILD_PROJECTS}"X" = "X" ]  ; then
    #遍历启动各个服务
    echo "所有的服务是:${ALL_SERVICE[@]}"
    echo "本机配置停止的服务${SERVICE_TO_BUILD[@]}"
	
	
	
	# 停止每个服务（循环SERVICETOBUID 数组）
    for service in ${ALL_SERVICE[@]}
        
    do
        #如果当前服务在本机配置的服务名和所有服务里面,那么他是需要停止的
        #取出本机配置可以停止的服务
        for real_stop_service in ${SERVICE_TO_BUILD[@]}
        do
            #如果当前服务在SERVICE_TO_BUILD,说明这个服务配置了停止,是需要停止的
            if [ ${service} == ${real_stop_service} ];then
                 # 停止服务
			    #echo  "正在停止服务${service}..." 		
			    func_service_stop ${service}	
			    # 停止单个服务
            fi
        done
    done
    
    sleep 20;
    # 杀死所有JAVA进程（暂时没设置更好的关闭服务的方法。后续修改。 可以按服务关闭或整体关闭）
	# 可以做为最后的手段。
	
    ps -aux|grep ${KEYWORD_FOR_GREP}|awk ' { if ($11=="java") { print $2 } }' |xargs kill -9
else

	echo "所有的服务是:${ALL_SERVICE[@]}"
    echo "本机配置停止的服务${SERVICE_TO_BUILD[@]}"
    echo "单独停止的服务是${BUILD_PROJECTS}"
	# 停止单个服务的情况，已经调用了关闭服务的方法。
    # 停止每个服务（循环SERVICETOBUID 数组）
    MY_BUILD=(${BUILD_PROJECTS})
    
    #循环所有的服务
    for service in ${ALL_SERVICE[@]}
        
    do
        #循环本机配置的服务名
        for real_stop_service in ${SERVICE_TO_BUILD[@]}
        do
            #如果当前服务在本机配置的服务名和所有服务里面,那么他是需要停止的
            if [ ${service} == ${real_stop_service} ];then
                #取出本次单独停止服务的服务
                for my_stop_service in ${MY_BUILD[@]}
                do
                    #如果当前服务在my_stop_service一样,说明是单独需要停止的服务
                    if [ ${service} == ${my_stop_service} ];then
                        # 停止服务
        			    echo  "正在停止服务${service}..." 		
        			    func_service_stop ${service}	
        			    # 停止单个服务
        			    sleep 5;
        			    get_service_file_name ${service}
        			    echo "ps -aux|grep ${SERVICE_FILE_NAME}|awk ' { if ($11=="java") { print $2 } }' |xargs kill -9"
        			    ps -aux|grep ${SERVICE_FILE_NAME}|awk ' { if ($11=="java") { print $2 } }' |xargs kill -9
                    fi
                done
            fi
        done
    done
fi








echo "停止脚本已完成。"
date +%F" "%H:%M:%S

