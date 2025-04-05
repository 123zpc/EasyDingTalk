## 项目概述

本项目是一个基于 Spring Boot 和 Feign 客户端的钉钉互动卡片集成应用，旨在简化与钉钉 API 的交互，提供群聊管理、用户信息查询和卡片消息发送、卡片互动回调等功能。项目通过 Feign 客户端封装了钉钉 API 的调用，并提供了 RESTful 接口供前端或其他服务调用。

## 功能说明

1. **获取访问令牌**：通过钉钉应用的 AppKey 和 AppSecret 获取访问令牌。
2. **用户信息查询**：根据手机号获取钉钉用户信息。
3. **群聊管理**：
   - 创建群聊
   - 获取群聊信息
4. **卡片消息发送**：
   - 群发卡片消息
   - 单发卡片消息
5. **回调处理**：处理钉钉回调请求，支持自定义回调逻辑。

## 使用说明

### 1. 环境

- Java JDK 17
- Maven 3.9.2
- Spring Boot 3.4.4

### 2. 创建钉钉应用

在钉钉开放平台创建一个企业内部应用，获取 AppKey 和 AppSecret。

### 3. 创建自定义机器人

进入刚刚创建的企业内部应用，创建自定义机器人，获取RobotCode。

### 4. 创建卡片模板

在钉钉开发者后台进入卡片平台，新建模板页面并发布。

### 5. 开通所需权限

在钉钉开发者后台为应用开通以下权限：
- **默认权限**
- **根据手机号获取成员基本信息**：用于查询用户信息。
- **钉钉群基础信息管理**：用于创建和管理群聊。
- **钉钉群基础信息读**：用于获取群聊信息。
- **互动卡片实例写权限**：用于发送卡片消息。


### 6. 配置钉钉应用信息

在 `application.properties` 文件中配置钉钉应用的相关信息：

```properties
# 钉钉应用配置
dingtalk.appKey=your_app_key
dingtalk.appSecret=your_app_secret
dingtalk.cardTemplateId=your_card_template_id
dingtalk.robotCode=your_robot_code
dingtalk.callbackType=STREAM
```


## 使用方法

### 1. 获取访问令牌

```http
GET /api/dingtalk/access-token
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "ok",
  "data": {
    "errcode": 0,
    "access_token": "your_access_token",
    "errmsg": "ok",
    "expires_in": 7200
  },
  "timestamp": 1696812345678
}
```

### 2. 根据手机号获取用户信息

```http
GET /api/dingtalk/user?accessToken={accessToken}&mobile={mobile}
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "ok",
  "data": {
    "userid": "user_id"
  },
  "timestamp": 1696812345678
}
```

### 3. 创建群聊

```http
POST /api/dingtalk/chat-group?accessToken={accessToken}
Content-Type: application/json

{
  "name": "群名称",
  "owner": "群主用户ID",
  "useridlist": ["成员用户ID1", "成员用户ID2"],
  "showHistoryType": 1,
  "searchable": 1,
  "validationType": 0,
  "mentionAllAuthority": 0,
  "managementType": 0,
  "chatBannedType": 0
}
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "ok",
  "data": {
    "errcode": 0,
    "errmsg": "ok",
    "chatid": "chat_id",
    "openConversationId": "open_conversation_id",
    "conversationTag": 0
  },
  "timestamp": 1696812345678
}
```

### 4. 获取群聊信息

```http
GET /api/dingtalk/chat-group/{chatId}?accessToken={accessToken}
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "ok",
  "data": {
    "owner": "群主用户ID",
    "showHistoryType": 1,
    "chatid": "chat_id",
    "validationType": 0,
    "useridlist": ["成员用户ID1", "成员用户ID2"],
    "icon": "群头像URL",
    "openConversationId": "open_conversation_id",
    "searchable": 1,
    "chatBannedType": 0,
    "managementType": 0,
    "mentionAllAuthority": 0,
    "conversationTag": 0,
    "name": "群名称",
    "status": 0
  },
  "timestamp": 1696812345678
}
```

### 5. 发送群聊卡片

```http
POST /api/dingtalk/group-card?accessToken={accessToken}&openConversationId={openConversationId}
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "ok",
  "data": {
    "deliverResults": [
      {
        "spaceId": "space_id",
        "spaceType": "space_type",
        "success": true,
        "carrierId": "carrier_id"
      }
    ],
    "outTrackId": "out_track_id"
  },
  "timestamp": 1696812345678
}
```

### 6. 发送私聊卡片

```http
POST /api/dingtalk/private-card?accessToken={accessToken}&userid={userid}
```

**响应示例**：

```json
{
  "code": 200,
  "msg": "ok",
  "data": {
    "deliverResults": [
      {
        "spaceId": "space_id",
        "spaceType": "space_type",
        "success": true,
        "carrierId": "carrier_id"
      }
    ],
    "outTrackId": "out_track_id"
  },
  "timestamp": 1696812345678
}
```
### 6. 卡片互动回调
**卡片按钮回调使用STREAM模式，监听和处理卡片互动按钮点击状态，可修改DefaultCallbackHandler或继承CustomCallbackHandler自定义处理**
### 7. 说明
**其余更多细节可参考钉钉官方文档**

## 项目结构

```
src
├── main
│   ├── java
│   │   └── com.dingtalk
│   │       ├── DingTalkApplication.java //项目的主类，用于启动 Spring Boot 应用
│   │       ├── config
│   │       │   └── DingTalkConfig.java //配置钉钉长连接客户端
│   │       ├── controller
│   │       │   └── DingTalkController.java //提供 RESTful API 接口，供测试调用
│   │       ├── feign  //封装钉钉 API 的 Feign 客户端
│   │       │   ├── DingTalkClient.java
│   │       │   └── InitCardInstanceClient.java
│   │       ├── handler //定义回调处理器接口，用于处理钉钉的回调请求
│   │       │   ├── CustomCallbackHandler.java
│   │       │   └── DefaultCallbackHandler.java
│   │       ├── listener //钉钉回调监听器，用于处理长连接的回调请求
│   │       │   └── DingTalkCallbackListener.java
│   │       ├── model
│   │       │   ├── request //定义请求对象
│   │       │   │   ├── DingTalkCreateChatRequest.java
│   │       │   │   └── initcard
│   │       │   │       ├── BaseDingTalkInitCardRequest.java
│   │       │   │       ├── GroupCardInitRequest.java
│   │       │   │       └── PrivateCardInitRequest.java
│   │       │   └── response //定义响应对象
│   │       │       ├── AccessTokenResponse.java
│   │       │       ├── CardCallbackResponse.java
│   │       │       ├── CardInstanceResponse.java
│   │       │       ├── DingTalkCreateChatResponse.java
│   │       │       ├── DingTalkGetChatResponse.java
│   │       │       └── DingTalkUserResponse.java
│   │       ├── response //定义统一的响应结果对象
│   │       │   ├── resenum
│   │       │   │   └── ReturnCode.java
│   │       │   └── resobj 
│   │       │       └── ResponseResult.java
│   │       └── service //定义钉钉服务的接口，声明业务方法
│   │           ├── DingTalkService.java
│   │           └── impl
│   │               └── DingTalkServiceImpl.java
│   └── resources //配置文件
│       ├── application.properties
│       └── static //测试html页面
│           └──index.html
└── test
    └── java
        └── com.dingtalk
            └── DingTalkApplicationTests.java
```

## 依赖项

项目使用了以下依赖项：
- Spring Boot Starter Web
- Spring Cloud OpenFeign
- Jackson Databind
- DingTalk Stream
- Lombok
- log4j
## 🌐 网页测试工具
  项目提供开箱即用的可视化测试界面，支持核心接口的交互式调试，位于src/main/resources/static/index.html