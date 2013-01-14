namespace java com.hyy.engine.thrift.generated

typedef i32    int
typedef i64    long
typedef string String

struct Person {
	1:long id,
	2:String name,
	3:int age = 0
}

service PersonService {
	void say(1:String content)
}