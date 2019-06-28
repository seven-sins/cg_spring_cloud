* [x] 注册中心、配置中心、网关等配置完成
* [ ] 系统模块完善中...

### 1. eureka-server

```yml
server:
  port: 8761
  name: eureka-server
spring:
  application:
    name: eureka-server

eureka:
  instance: 
    hostname: 127.0.0.1
    prefer-ip-address: true
  client:
    # 设置是否将自己作为客户端注册到注册中心(缺省true)
    register-with-eureka: false
    # 设置是否从注册中心获取注册信息(缺省true)
    # 因为只有一个eurekaServer, 不需要从其他节点同步数据, 设为false
    fetch-registry: false
    # 集群配置多个, 逗号分隔
    serverUrl:
      defaultZone: http://${eureka.instance.hostname}:{server.port}/eureka/
    healthcheck:
      enabled: true
  server:
    # 清理已关闭的节点(默认60秒)
    eviction-internal-timer-in-ms: 60000

endpoints:
  enabled: false
  info:
    enabled: true
  health:
    enabled: true
```

## 

## 2. config-server

``` yml
server:
  port: 8888

spring:
  application:
    name: config-server
  profiles:
    active: native
  cloud:
    config:
      server:
        native:
        	# 配置文件存储位置
          searchLocations: file:/Users/seven/Documents/projects/mine/config
        encrypt:
          enabled: false

eureka:
  instance:
    hostname: 127.0.0.1
    # 将ip注册到eureka-server
    prefer-ip-address: true
    instance-id: ${spring.application.name}:${spring.application.instance_id:${server.port}}
  client:
    serverUrl:
      defaultZone: http://${eureka.instance.hostname}:8761/eureka/
    healthcheck:
      enabled: true

endpoints:
  enabled: false
  info:
    enabled: true
  health:
    enabled: true
    
```

## 3. gateway-server

```yml
server:
  port: 6638

spring:
  application:
    name: gateway-server
    
eureka:
  instance:
    hostname: 127.0.0.1
    prefer-ip-address: true
    instance-id: ${spring.application.name}:${spring.application.instance_id:${server.port}}
  client:
    serverUrl:
      defaultZone: http://${eureka.instance.hostname}:8761/eureka/
    healthcheck:
      enabled: true
      
endpoints:
  enabled: false
  info:
    enabled: true
  health:
    enabled: true

# hystrix超时时间
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 3000

# 动态路由
zuul:
  host:
    maxTotalConnections: 100
    maxPerRouteConnections: 100
  routes:
    sys-server:
      path: /rest/sys/**
      stripPrefix: false
      serviceId: sys-server
    
```



