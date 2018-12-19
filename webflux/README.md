参考：https://blog.csdn.net/get_set/article/details/79480233

docker pull mongo:3.2
docker run -p 27017:27017 -v $PWD/db:/data/db -d mongo:3.2

http://localhost:8080/user/ 保存
{
    "id": "5a9504a167646d057051e229",
    "username": "zhangsan",
    "name": "张三",
    "phone": "18610861861",
    "birthday": "1991/1/1"
}

根据用户名查询（METHOD:GET URL:http://localhost:8080/user/zhangsan）

查询全部（METHOD:GET URL:http://localhost:8080/user）

METHOD:DELETE URL:http://localhost:8080/user/zhangsan），返回值为1