# 土壤水势 
  <br>----web 客户端

# 土壤水势项目API接口文档

@[toc]
## 通用说明:

### ①接口通用前缀:

> 测试环境: [http://192.168.0.168:8081/wellLid](http://192.168.0.168:8081/wellLid)
>
> 生产环境: [待定](http://192.168.0.168:8081/wellLid)

### ② 接口通用返回:

```json
{
    "status": 0,
    "msg": "查询状态",
    "data": [

    ]
}
```
------

## 1 查询

### 1.1 查询某天的某节点水势信息

- **说明**
> 提供测试的账号
> appType=2&userName=demo&passWord=123456
>
- **请求URL**

> [/soil/selectSoilSet.do](#)

- **请求方式** 

> **GET**

- **请求参数**

| 请求参数 | 必选 | 参数类型             | 说明                                      |
| :------- | :--- | :------------------- | :---------------------------------------- |
| node  | true | <mark>Integer</mark> |  结点号 |
| day | true | <mark>String</mark>  | 日期（2019-09-22）                                   |
                                    |
> response

success
```json
{
    "status": 0,
    "msg": "查询成功",
    "data": [
        {
            "node": 1,
            "humidity": 2.66,
            "times": "2019-09-22- 00:00:29"
        },
        {
            "node": 1,
            "humidity": 2.68,
            "times": "2019-09-22- 00:20:17"
        }
    ]
}
```
