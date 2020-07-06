#!/bin/bash
###############################################################################
# 功能:   综合财富平台云架构标准程序 节点服务停止程序
# 描述:   1.停止服务。
###############################################################################



###############################################################################
#  程序大纲:
#	一、 配置部分
#	二、 函数部分
#	三、正式逻辑
#
#  程序逻辑：
#    一、参数校验与转换
#    二、判断待停止的服务（所有服务【仅发布包里的】、指定服务）
#    三、停止服务
###############################################################################

###############################################################################
#  配置部分
APP_DIR="/home/iwss/app/iwss"

#定义 所有要停止的业务项目 
#SERVICEARRAY=("iwsseurekaserver" "iwssconfigserver" "iwssgateway" "iwssservicetran" "iwssbatchta" "sysman" "iwssservicemngr" "iwssservicequota")
SERVICEARRAY=("iwsseurekaserver" "iwssconfigserver" "iwssgateway" "sysman" "transman" "standardif" "retailif" "adaptif" "productman")


#定义一个map ，用于存放每个服务部署的细节信息
declare -A servicemap;


# 配置部分结束
###############################################################################


###############################################################################
#  函数部分

# 根据服务名取得端口
# 参数: 1 服务名，例如 wxpreweb
# 说明：通过ps 命令，从系统进程中取得启动服务时的命令，命令行中包含-Dserver.port=
#       从其中取得端口号
# 返回：端口号,如果未找到-Dserver.port参数，则返回nothing
#
function get_service_port()
{
	
	# 使用PS命令取得命令行参数 （传入参数为服务名）
	COMMANDS_LINE=`ps -aux|grep ${1}|awk ' { if ($11=="java") { print  } }'`
	
	
	if [ "$COMMANDS_LINE" == "" ] ; then
		break
	fi

	# 使用空格拆分命令行为数组
	COM_PARAM_ARRAY=(${COMMANDS_LINE})
	
	#echo "正在根据服务名${1}获取端口 "   # 有可能端口与命令行指定不一致，待调整为一致。

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
	return 0

}

# 停止单个服务（使用端口号）
# 参数：  参数1 端口号
#         参数2 服务名 ，用于强行关闭
# 说明：  根据服务名取得端口，然后进行停止（测试环境暂时直接kill）
# 不指定打包使用的配置文件 ，在启动时处理配置文件。
function func_service_stop_port()
{
	
	
	# 优雅的停止服务，需要先取得端口
	port=${1}
	echo "当前正在根据端口停止服务,端口是${1}! \n"
	
	#echo "`curl -X POST http://localhost:${port}/shutdown ` "
	# 使用curl 发送停止命令 ，如果需要密码，在参数中添加密码
	HTTPRESULT=`curl -X POST http://localhost:${port}/actuator/shutdown ` 
	
	RESULT=$(echo $HTTPRESULT | grep 'Shutting' )
	if [[ "$RESULT" != "" ]]
	then
		# 等待后台任务执行完成。 时间根据最长业务时间确定
		sleep 5
		echo "${2}服务已经成功关闭 "
	else
		# 关闭服务响应不正确。 可能需要再次检查端口和服务状态。。。
		echo "${2}关闭服务响应不正确，请检查服务是否关闭 "
		# 或者考虑强行关闭一次(可能会损失未提交事务。)
		ps -aux|grep ${2}|awk ' { if ($11=="java") { print $2 } }' |xargs kill -9
	fi
		
	


}
# 停止服务
# 参数: 参数1 服务名
function func_stop_service()
{
	# 应用配置
	# 没有服务名则退出。
	if [ "${1}x" == "x" ]; then
		break
	fi
	
	echo  "正在关闭服务 $1 ..."
	# 取得port
	port=` get_service_port "$1" `
		
	echo "获取到服务使用的端口为 $port "
	# 没有获取到端口则退出
	if [ "$port" == "0" -o "$port" == "" ] ; then
		break
		echo  "未找到服务 $1 跳过"
	fi
	
	func_service_stop_port  $port  $1
	
	echo  "已关闭服务 $1 "
}



#  函数部分结束
###############################################################################


###############################################################################
# 脚本正式逻辑
# 逻辑描述: 一、处理参数
#           二、判断要停止的服务
#			三、根据情况停止指定服务或停止所有服务
# 注意事项:
#           
#
###############################


# 参数预处理区
# 处理一下命令行参数，并赋值到参数变量中。  添加参数需要同样的格式。
# 参数 -p|--projects   要停止的项目，使用空格分隔

GETOPT_ARGS=`getopt -o p:l: -al projects:,local-ip: -- "$@" `
eval  set -- "$GETOPT_ARGS"

while true ; do
        case "$1"  in                
				-p|--projects) PROJECTS="$2" ; shift 2 ;;
				-l|--local-ip) LOG_HOST="$2" ;  shift 2 ;;
                --) shift; break ;;
                #*)  show_usage exit 1 ;
        esac
done


# 要停止的项目
SERVICETODO=""
# 处理要停止的服务
# 基本逻辑： 不指定服务的情况下，按服务包里jar包列表停止。
#            注意：有可能不在服务包中，但启动的情况（启动后删除包？。。。是否可能？）
# 先处理要停止的列表
if [ ${PROJECTS}"x" == "allx" -o ${PROJECTS}"x" == "x" ] ; then
	# 循环构建后台服务 并停止 如果命令行没有参数，就全部构建
	# 循环构建每个服务（先构建，再停止 ，然后停止)  未考虑构建失败的情况，需要关注输出！
	
	SERVICETODO=${SERVICEARRAY[@]}
else
	#拆分成数组
	SERVICETODO=(${PROJECTS})
fi

# 循环处理指定的服务进行停止
for service in ${SERVICETODO[@]}
do	
		# 如果服务包存在，则停止  （新部署的话没有服务包，也没有服务，后续补充判断）
		findservice=`ls ${APP_DIR}| grep ${service} | wc -l`
		if [ "$findservice" == "1" ] ; then 
		
			func_stop_service $service 
		fi
done






#  正式逻辑结束
###############################################################################





