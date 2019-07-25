# 1. 安装zookeeper

```shell
# 下载
wget https://mirrors.tuna.tsinghua.edu.cn/apache/zookeeper/zookeeper-3.4.14/zookeeper-3.4.14.tar.gz
# 解压
tar -zxvf apache-zookeeper-3.4.14.tar.gz
# 重命名
mv apache-zookeeper-3.4.14.tar.gz zookeeper-3.4.14
# 重命名配置文件
cd zookeeper-3.4.14
cd conf
cp zoo_sample.cfg zoo.cfg
vi zoo.cfg
# 单机模式只修改数据存储目录, 其他默认
dataDir=/deploy/zookeeper_data
# 配置环境变量
vi /etc/profile
# 快捷键shift+g移到文件末尾
# 添加zk环境变量
ZK_HOME=/deploy/zookeeper-3.4.14
PATH=$ZK_HOME/bin:$PATH
# 启动(使用zkServer.sh start-foreground可以查看启动日志)(3.5.5启动提示找不到主类)
zkServer.sh start

```





# 2. 安装kafka

```shell
# kafka依赖zookeeper
# 下载
wget http://mirrors.tuna.tsinghua.edu.cn/apache/kafka/2.2.0/kafka_2.11-2.2.0.tgz
# 解压
tar -zxvf kafka_2.11-2.2.0.tgz
# 进入目录修改配置
cd kafka_2.11-2.2.0/config
vi server.properties
# 修改配置(其他保持默认,默认连接本机2181端口zookeeper) 
log.dirs=/deploy/kafka_log
# 启动
# 进入bin目录
./kafka-server-start.sh ../config/server.properties
# 命令创建主题
./kafka-topics.sh --create --zookeeper 127.0.0.1:2181 --partitions 1 --replication-factor 1 --topic testTopic
# 生产者
./kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic testTopic
# 消费者
./kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic testTopic



#kafka后台启动
nohup ./kafka-server-start.sh ../config/server.properties 2>&1 &

```





# 3. 安装hadoop

##### 下载地址

```shell
http://archive.cloudera.com/cdh5/cdh/5/
wget http://archive.cloudera.com/cdh5/cdh/5/hadoop-2.6.0-cdh5.9.3.tar.gz
```

##### master(以下配置master都指本机，类似xxx.master.com)

~~~shell
# 修改主机名, 将主机名改为master
vi /etc/hostname
# 修改hosts, 添加192.168.0.205 master
vi /etc/hosts
# ssh免密
ssh-keygen -t rsa
ssh-copy-id master
# 操作完成后查看authorized_keys文件
ls -a ~/.ssh
ssh master
~~~

##### 安装hadoop

```shell
# wget下载慢, 使用csdn下载的版本
# 解压
tar -zxvf hadoop-2.6.0.tar.gz
cd hadoop-2.6.0.tar.gz
cd etc/hadoop
# 修改hadoop-env.sh, 修改JAVA_HOME, 使用whereis java查看安装目录
vi hadoop-env.sh
export JAVA_HOME=/usr/java/jdk1.8.0_152
# 1.修改core-site.xml
vi core-site.xml
<configuration>
	<property>
		<name>fs.default.name</name>
		<value>hdfs://master:9000</value>
    </property>
    <property>
		<name>hadoop.tmp.dir</name>
		<value>/deploy/hadoop_data/tmp</value>
    </property>
</configuration>
# 2.修改hdfs-site.xml
vi hdfs-site.xml
<configuration>
	<property>
		<name>dfs.replication</name>
		<value>1</value>
    </property>
    <property>
		<name>dfs.permissions</name>
		<value>true</value>
    </property>
</configuration>
# 3.修改mapred-site.xml, 先复制模板
cp mapred-site.xml.template mapred-site.xml
vi mapred-site.xml
<configuration>
	<property>
		<name>mapreduce.framework.name</name>
		<value>yarn</value>
    </property>
    <property>
		<name>mapreduce.jobhistory.address</name>
		<value>master:10020</value>
    </property>
</configuration>
# 4.修改yarn-site.xml
vi yarn-site.xml
<configuration>
	<property>
		<name>yarn.resourcemanager.hostname</name>
		<value>master</value>
    </property>
    <property>
		<name>yar.nodemanager.aux-services</name>
		<value>mapreduce_shuffle</value>
    </property>
    <property>
		<name>mapreduce.job.ubertask.enable</name>
		<value>true</value>
    </property>
</configuration>
# 5.修改环境变量
vi /etc/profile
export JAVA_HOME=/usr/java/jdk1.8.0_152
export HADOOP_HOME=/deploy/hadoop-2.6.0
export PATH=.:$JAVA_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PATH
# 让修改的环境变量生效
source /etc/profile
# 执行jps查看进程
jps
# 格式化
hadoop namenode -format
# 启动dfs
start-dfs.sh
# 启动yarn
start-yarn.sh
# 启动成功后访问web
http://192.168.0.205:50070
```





# 4. 安装hbase

```shell
# hbase依赖zookeeper
wget http://archive.cloudera.com/cdh5/cdh/5/hbase-1.2.0-cdh5.9.3.tar.gz
# (官方下载太慢, 使用csdn下载的版本)
tar -zxvf hbase-1.3.2-bin.tar.gz
cd hbase-1.3.2/conf
# 1.修改hbase-env.sh
export JAVA_HOME=/usr/java/jdk1.8.0_152
# 2.修改hbase-site.xm;
vi hbase-site.xml
<configuration>
	<property>
        <name>hbase.master.port</name>
        <value>16000</value>
    </property>

    <property>
        <name>hbase.master.info.port</name>
        <value>16010</value>
    </property>

    <property>
        <name>hbase.regionserver.port</name>
        <value>16201</value>
    </property>

    <property>
        <name>hbase.regionserver.info.port</name>
        <value>16301</value>
    </property>
	<property>
		<name>hbase.rootdir</name>
		<value>hdfs://master:9000/hbase</value>
    </property>
    <property>
		<name>hbase.cluster.distributed</name>
		<value>true</value>
    </property>
    <property>
		<name>hbase.zookeeper.quorum</name>
		<value>master</value>
    </property>
    <property>
		<name>dfs.replication</name>
		<value>1</value>
    </property>
</configuration>
# 3.配置regionservers, 将localhost改为master
vi regionservers
# 4.修改环境变量
vi /etc/profile
export JAVA_HOME=/usr/java/jdk1.8.0_152
export HADOOP_HOME=/deploy/hadoop-2.6.0
export HBASE_HOME=/deploy/hbase-1.3.2
export PATH=.:$JAVA_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$HBASE_HOME/bin:$PATH
source /etc/profile
# 5.启动hbase
start-hbase.sh
# 6.使用jps查看
jps
18866 Kafka
32482 HMaster
29156 NameNode
29414 SecondaryNameNode
29558 ResourceManager
29271 DataNode
4713 QuorumPeerMain
32601 HRegionServer
29642 NodeManager
# 7.web访问
http://192.168.0.205:16010

```





# 5. 安装elasticsearch

#### elasticsearch7.x要求Java11

```shell
# 下载elasticsearch7.2.0
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.2.0-linux-x86_64.tar.gz
# 解压
tar -zxvf elasticsearch-7.2.0-linux-x86_64.tar.gz
# 编辑config/elasticsearch.yml
vi elasticsearch.yml
path.data: /deploy/elasticsearch_data
path.logs: /deploy/elasticsearch_log
network.host: 192.168.0.205
http.port: 9200
cluster.name: my_es
node.name: node1
# 添加elasticsearch用户
useradd es
passwd es
# 给目录授权
chown -R es:es /deploy/elasticsearch_data
chown -R es:es /deploy/elasticsearch_log
chown -R es:es /deploy/elasticsearch-7.2.0
# 1.使用root用户编辑/etc/security/limits.conf
# 添加以下内容
* soft nofile 65536
* hard nofile 131072
* soft nproc 2048
* hard nproc 4096
# 2.root用户编辑vi /etc/sysctl.conf
# 添加下面配置：
vm.max_map_count=655360
#配置完成执行命令
sysctl -p
# 启动(切换到es用户下启动)
su es
/deploy/elasticsearch-7.2.0/bin/elasticsearch -d

# web访问
http://192.168.0.205:9200/
```



#### 安装elasticsearch6.x

```shell
# 下载
wget  https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.4.2.tar.gz
# 解压
tar -zxvf elasticsearch-6.4.2.tar.gz
# 安装方法同7.x

# web访问
http://192.168.0.205:9200/
```



##### 查看集群状态

```shell
http://192.168.0.205:9200/_cluster/health?pretty=true
```



