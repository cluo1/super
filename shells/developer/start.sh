#!/bin/sh
################################################################################
# 作者:   史骏马  shijunma@joyintech.com
# 时间:   2019年10月14日
# 功能:   综合财富平台管理端程序打包脚本
# 描述:   一、所有参数都有默认值，也可以在命令行参数中指定。
#			指定参数: 
#			-b 或--base-dir  定义程序主目录所在位置  默认/home/finance/finance
#				例 -b /u01/app 或  --base-dir=/u01/app
#               -d|--dist-dir) DIST_DIR 目标目录
#				-v|--svn-version) SVN_VERSION   SVN版本
#				-t|--skip-test) SKIPTEST       忽略MAVEN测试  默认忽略
#				-s|--skip-starter) SKIPSTARTER  忽略基本依赖构建 默认不忽略
#				-p|--build-projects) BUILD_PROJECTS   要构建的服务，逗号分隔
#				-l|--local_ip) LOG_HOST                本机IP地址
#			
################################################################################

# 使用提示
function show_usage()
{
	echo "综合财富平台微信端服务程序启动脚本"
	echo "所有参数都有默认值，也可以在命令行参数中指定。 \n "
	echo "		指定参数: \n		"
	echo "		-b 或--base-dir  定义程序主目录所在位置  默认/u01/app/finance/source \n "
	echo "		-d|--dist-dir) DIST_DIR 目标目录   \n "
	echo "		-v|--svn-version) SVN_VERSION   SVN版本  \n "
	echo "		-t|--skip-test) SKIPTEST       忽略MAVEN测试  默认忽略 \n "
	echo "		-s|--skip-starter) SKIPSTARTER  忽略基本依赖构建 默认不忽略 \n "
	echo "		-p|--build-projects) BUILD_PROJECTS   要构建的服务，逗号分隔 \n "
	echo "		-l|--local_ip) LOG_HOST                本机IP地址 \n "
	
	echo "-h 或 --help 显示帮助参数信息 "
}

echo "开始构建 :"
date +%F" "%H:%M:%S
#定义打包时间
NOW_TIME=$(date +$Y$m$d_$H$M$S)
show_usage

######################################所有启动脚本可能修改地方##########################################
#参数定义
#每台机器不一样的配置
#用户，用户不对无法启动
BASE_USER=finance

DIFFERENT_CONF=/data2/finance/shbin/differentConf

# 定义app服务jar包上传路径,从此路径中复制到运行路径
UPLOAD_DIR=/data2/finance/upload

#定义app服务jar包备份路径
APP_BAK_DIR=/data2/finance/backup

# 定义程序主目录
BASE_DIR=/data2/finance/app/finance

# 定义日志目录
LOG_DIR=/data2/finance/logs

#定义nohup日志目录
NOHUP_LOG_DIR=/data2/finance/testlog




 

 
# 定义参数
# eureka.client.serviceUrl.defaultZone      ,服务地址可以配多个，逗号分隔
EUREKA_PASSWORD='Joyintech2018'
#EUREKA_URL='http://joyintech:Joyintech2018@192.168.2.153:8100/eureka/'
EUREKA_URL='http://joyintech:Joyintech2018@10.194.15.160:8100/eureka/,http://joyintech:Joyintech2018@10.194.15.161:8100/eureka/'
# 使用的配置文件。  'dev' 'test' 'prod'   目录使用这三个开发、测试 、生产
ACTIVE_PROFILE='test'

# 数据源连接（ 集群使用SCAN地址  或切换为集群连接字符串 ）
DATASOURCE_URL="jdbc:oracle:thin:@10.193.80.104:1521/orcl"
DATASOURCE_USER="keyboardtest"
#DATASOURCE_PASS="keyboardtest"
DATASOURCE_PASS_DRUID="DElmZcAA+roBiYXx9+A+s0+B7jvShhwEcKpqXoj30ofEwYI8la8Pwk0wEEksw9cvj+YO3tc2uQ3fVpDzEIsVfA=="
JOYIN_DRUID_PUBLIC_KEY="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALuAGvcch7eQGGikzNsf+HZZ8OtymPINyrwUWxOgQ8R5A71A4f/AlCqwprOo/aNMporUN4o+qwPTV7iLcpmrFkkCAwEAAQ=="

######################################所有启动脚本可能修改地方##########################################

######################################所有启动脚本基本不修改地方##########################################



#定义TOMCAT环境变量#注意事项:一头一尾不能有空格!
TOMCAT_OPTIONS=""

#定义程序参数变量#注意事项:一头一尾不能有空格!
PARAM_OPTIONS=""



#本机端口,用于日志里面
#需要修改
LOG_HOST=""

#从shbin的differentConf里面读取本机IP（ib2使用）、本机别名、本机应该启动的服务参数
source ${DIFFERENT_CONF}

LOG_HOST=$different_service_id

JOYIN_REAL_HOST=$different_service_real_ip

echo "LOG_HOST=$LOG_HOST,JOYIN_REAL_HOST=$JOYIN_REAL_HOST "

##定义 启动的项目
SERVICE_TO_BUILD=""
SERVICE_TO_BUILD=$different_service_name

#定义全部的服务,主要是顺序从这里取
ALL_SERVICE=$start_all_server

#定义全局变量,服务配置,服务名,启动端口
SERVICE_CONFIG=""
SERVICE_NAME=""
SERVICE_START_PORT=""
SERVICE_FILE_NAME=""

######################################所有启动脚本基本不修改地方##########################################


################################################################################
# 函数定义区

# 函数，判断端口是否已经占用
function is_port_used()
{
	# 待添加判断端口占用情况，如果占用，持续加1
	#PORTUSEDCOUNT=$(  netstat -an|grep ${1}|wc -l )
	#root用户才能用-lnp!
	PORTUSEDCOUNT=netstat -lnp|grep ${1}|wc -l
	if [ ${PORTUSEDCOUNT}>0 ] ; then
		return 1;
	else
		return 0;
	fi
	
}

#函数, 判断服务文件是否存在,不存在会退出脚本
function check_service_file_exist()
{
    SERVICE_FILE_NAME_NUMBER=""
    SERVICE_FILE_NAME_NUMBER="`ls  ${BASE_DIR}  | grep ${SERVICE_NAME} |grep jar|grep -v "_" |wc -l`"
    
    SERVICE_FILE_NAME=""
    
    if [ ${SERVICE_FILE_NAME_NUMBER}"x" == "1x" ] ; then
		SERVICE_FILE_NAME="`ls  ${BASE_DIR}  | grep ${SERVICE_NAME} |grep jar|grep -v "_"`"
		echo "当前服务${SERVICE_NAME}的jar文件名是$SERVICE_FILE_NAME,文件个数是${SERVICE_FILE_NAME_NUMBER}"
	else
		echo "==========================================ERROE================================================================="
		echo "The number of ${SERVICE_NAME} file is ${SERVICE_FILE_NAME_NUMBER}, is not only one;";
		echo "error ! Please check this service file in ${BASE_DIR} !" ;
		echo " ${SERVICE_NAME}服务的文件在${BASE_DIR}不存在,或者文件不止一个,请确认文件是否正确!";
		echo "(注意:脚本会根据服务名搜文件,排除带“_”的服务名)";
	fi
	
}


#函数, 组装启动参数
function build_param_start_service()
{
    #定义业务服务启动参数#不变的参数
    BUSI_SERVER_PARAM=""
    BUSI_SERVER_PARAM="${BUSI_SERVER_PARAM} -Xms${SERVICE_CONFIG[0]} "
    BUSI_SERVER_PARAM="${BUSI_SERVER_PARAM} -Xmx${SERVICE_CONFIG[1]} "
    
    BUSI_SERVER_PARAM="${BUSI_SERVER_PARAM} ${PARAM_OPTIONS}"
    BUSI_SERVER_PARAM="${BUSI_SERVER_PARAM} -Dserver.port=${SERVICE_START_PORT} " 
    BUSI_SERVER_PARAM="${BUSI_SERVER_PARAM} -Djoyin.log.name=${SERVICE_NAME}"
    BUSI_SERVER_PARAM="${BUSI_SERVER_PARAM} -Deureka.instance.preferIpAdress=true"
    BUSI_SERVER_PARAM="${BUSI_SERVER_PARAM} -Deureka.instance.instance-id=${LOG_HOST}:${SERVICE_START_PORT}"


    #不同的服务添加不同的参数
    if [ ${SERVICE_NAME} == "eurekaserver" ];then
        BUSI_SERVER_PARAM="${BUSI_SERVER_PARAM} ${EUREKA_SERVER_PARAM}"
    elif [ ${SERVICE_NAME} == "configserver" ];then
        BUSI_SERVER_PARAM="${BUSI_SERVER_PARAM} ${CONFIG_SERVER_PARAM}"
    else
        echo " ";
    fi
    
    # 启动服务
    echo  "正在启动服务${service}...端口${SERVICE_START_PORT}"
    # 打印启动命令观察启动参数是否正确
    echo "nohup java ${BUSI_SERVER_PARAM} -jar ${BASE_DIR}/${SERVICE_FILE_NAME} > ${NOHUP_LOG_DIR}/${SERVICE_NAME}.log &";
    source /etc/profile
    #nohup java ${BUSI_SERVER_PARAM} -jar ${BASE_DIR}/${SERVICE_FILE_NAME} > /dev/null 2>&1 &
    nohup java ${BUSI_SERVER_PARAM} -jar ${BASE_DIR}/${SERVICE_FILE_NAME} > ${NOHUP_LOG_DIR}/${SERVICE_NAME}.log &

    #不同的服务等待不同的时间
    if [ ${SERVICE_NAME} == "eurekaserver" ];then
        sleep 25;
    elif [ ${SERVICE_NAME} == "configserver" ];then
        sleep 40;
    else
        sleep 1;
    fi
    
}

################################################################################
# 参数处理区
# 处理一下命令行参数，并赋值到参数变量中。  添加参数需要同样的格式。

GETOPT_ARGS=`getopt -o b:d:v:t:s:p:l: -al base-dir:,dist-dir:,svn-version:,skip-test:,skip-starter:,build-projects:,local-ip: -- "$@" `
eval  set -- "$GETOPT_ARGS"

while true ; do
        case "$1"  in
                -b|--base-dir) BASE_DIR="$2"; shift 2 ;;
                -d|--dist-dir) DIST_DIR="$2"; shift 2 ;;
				-v|--svn-version) SVN_VERSION="$2" ; shift 2 ;;
				-t|--skip-test) SKIPTEST="$2" ; shift 2 ;;
				-s|--skip-starter) SKIPSTARTER="$2" ; shift 2 ;;
				-p|--build-projects) BUILD_PROJECTS="$2" ; shift 2 ;;
				-l|--local-ip) LOG_HOST="$2" ;  shift 2 ;;
                --) shift; break ;;
                *)  show_usage exit 1 ;
        esac
done


###############################################################################
# 处理完参数后，组合到服务的启动参数中
PARAM_OPTIONS=" "
PARAM_OPTIONS=" ${PARAM_OPTIONS}  -Dspring.cloud.config.profile=${ACTIVE_PROFILE} "

#增加日志路径
PARAM_OPTIONS=" ${PARAM_OPTIONS}  -Djoyin.log.dir=${LOG_DIR} "

#增加机器的ip
PARAM_OPTIONS=" ${PARAM_OPTIONS}  -Djoyin.log.host=${LOG_HOST} "

PARAM_OPTIONS=" ${PARAM_OPTIONS}  -Djoyin.ib2.host=${JOYIN_REAL_HOST} "

PARAM_OPTIONS="${PARAM_OPTIONS}  -Deureka.client.serviceUrl.defaultZone=${EUREKA_URL}"



#eureka的配置
EUREKA_SERVER_PARAM=""
#EUREKA_SERVER_PARAM="${EUREKA_SERVER_PARAM}  -Deureka.client.serviceUrl.defaultZone=${EUREKA_URL}"
EUREKA_SERVER_PARAM="${EUREKA_SERVER_PARAM}  -Dsecurity.user.password=${EUREKA_PASSWORD}"
EUREKA_SERVER_PARAM="${EUREKA_SERVER_PARAM}  -Dhystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000 "
#注册中心的保护机制,为true时,默认心跳三次超时后剔除服务
EUREKA_SERVER_PARAM="${EUREKA_SERVER_PARAM}  -Deureka.server.enable-self-preservation=true"

#config的配置
CONFIG_SERVER_PARAM=""
#CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM}  -Deureka.client.serviceUrl.defaultZone=${EUREKA_URL}"
CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Dspring.datasource.url=${DATASOURCE_URL}"
CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Dspring.datasource.username=${DATASOURCE_USER}"   	
#CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Dspring.datasource.password=${DATASOURCE_PASS}"
CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Dspring.datasource.password=${DATASOURCE_PASS_DRUID}"
CONFIG_SERVER_PARAM="${CONFIG_SERVER_PARAM} -Djoyin.druid.public.key=${JOYIN_DRUID_PUBLIC_KEY}"

###############################################################################

###############################################################################
# 脚本正式逻辑
# 逻辑描述: 一、按照servicemap里面的顺序启动,
#            再根据differentConf里面配置的different_service_name决定启动什么服务
#			二、第一启动eurekaserver,
#			三、第二启动configserver (需要eurekaserver 启动成功）
#           四、第三启动启动gateway
#			四、启动其他节点
# 注意事项:
#           一、需要添加判断eurekaserver是否已启动。
#			二、注册中心、配置中心、网关端口固定 。
#			三、其他服务端口使用servicemap里面的端口
#
###############################

#判断当前登录的用户是否为finance
user=$(whoami)
if [ "$user" == "$BASE_USER"  ]
  then
    echo "当前用户是finance，允许执行" 
  else
    echo "当前用户是${user},不是finance,不允许执行"
    exit; 
fi


# 判断确认主目录存在
if [ ! -d "${BASE_DIR}" ] ; then
	echo "主目录不存在，请先确认主目录正确！"
	exit 1;
fi


UPLOAD_FILE_NUMBER="`ls  ${UPLOAD_DIR}  | grep jar |wc -l`"

#
if [ ${UPLOAD_FILE_NUMBER}"x" == "0x" ] ; then
	echo "上传包目录${UPLOAD_DIR}没有文件"
else
	echo "上传包目录${UPLOAD_DIR}有文件${UPLOAD_FILE_NUMBER}个,开始备份压缩"
	
	#备份原先jar包
    echo "tar -cvPf ${APP_BAK_DIR}/finance_"$(date +%F"_"%H_%M_%S)".tar ${BASE_DIR}/*.jar";
    #tar -cvPf ${APP_BAK_DIR}/finance_"$(date +%F"_"%H_%M_%S)".tar ${BASE_DIR}/*.jar
	
	#复制主程序文件到程序主目录
    echo "cp -f ${UPLOAD_DIR}/*.jar ${BASE_DIR}进入jar包上传路径";
    cp -f ${UPLOAD_DIR}/*.jar ${BASE_DIR}

    #赋予jar可执行权限
    echo "chmod +x ${BASE_DIR}/*.jar";
    chmod +x ${BASE_DIR}/*.jar

    #md5对比jar
    echo "md5sum ${UPLOAD_DIR}/*.jar";
    md5sum ${UPLOAD_DIR}/*.jar

    echo "md5sum ${BASE_DIR}/*.jar";
    md5sum ${BASE_DIR}/*.jar


    #删除上传的jar
    echo "rm -rf ${UPLOAD_DIR}/*.jar";
    rm -rf ${UPLOAD_DIR}/*.jar
fi

###############################################################################

#遍历启动各个服务
echo "所有的服务:${ALL_SERVICE[@]}"
echo "本机配置启动的服务${SERVICE_TO_BUILD[@]}"
for service in ${ALL_SERVICE[@]}
    
do
    #遍历从differentConf配置里面的different_service_name,也就是SERVICE_TO_BUILD
    for real_start_service in ${SERVICE_TO_BUILD[@]}
    do
        #如果当前服务在SERVICE_TO_BUILD,说明这个服务配置了启动,是需要启动的
        if [ ${service} == ${real_start_service} ];then
            SERVICE_NAME=${service}
            echo "服务${SERVICE_NAME}准备启动!";
            
            #从servicemap里面根据服务名取出服务对应的内存大小和端口
            SERVICE_CONFIG=(${servicemap[${SERVICE_NAME}]})
            SERVICE_START_PORT=${SERVICE_CONFIG[2]}
            
            #判断端口是否被占用,如果被占用
            #while [ $(netstat -lnp|grep ${SERVICE_START_PORT}|wc -l)"x" != "0x" ] ; do
		    #    SERVICE_START_PORT=$((${SERVICE_START_PORT}+1))
	        #done
	        
	        #调用函数判断服务文件是否存在
            check_service_file_exist
	        
	        #判断服务是否启动
	        BUSI_PROCESSID=$(ps -aux | grep ${SERVICE_FILE_NAME}| awk '{ if($11=="java") { print $2} } ' )
    		if [ ! -z "${BUSI_PROCESSID}" ] ; then
    			echo "${SERVICE_NAME} 服务已经启动! 0 ${SERVICE_CONFIG[0]} 1 ${SERVICE_CONFIG[1]} 2 ${SERVICE_CONFIG[2]}";
    		else
                #如果有这个服务的jar包在,那么构建启动参数和启动服务
                if [ ${SERVICE_FILE_NAME}"x" != "x" ] ; then
                    #启动服务
    			    build_param_start_service
                fi
    		fi
        fi
    done
done


echo "打印nohup结尾示例 > ${NOHUP_LOG_DIR}/sysman.log &";
echo "启动完成,请检查启动的程序是否正确。"
date +%F" "%H:%M:%S





