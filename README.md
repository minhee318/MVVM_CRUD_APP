# MVVM_CRUD_APP
MVVM패턴, 코루틴, Flow, AAC를 공부합니다.

## db
> Room에는 3가지 구성 요소가 있다. 
* Entity : Database안에 있는 테이블을 Java나 Kotlin클래스로 나타낸 것. 데이터 모델 클래스
* DAO : Database Access Object, 데이터베이스에 접근해서 실질적으로 insert, delete 등을 수행하는 메소드를 포함한다.
* Database : database holder를 포함하며, 앱에 영구 저장되는 데이터와 기본 연결을 위한 주 액세스 지점이다. RoomDatabase를 extend 하는 추상 클래스여야 하며, 테이블과 버전을 정의하는 곳이다.
* Repository : DB인스턴스는 여기서 호출하여 사용!

![스크린샷 2022-01-17 오후 11 02 00](https://user-images.githubusercontent.com/52806967/149782479-f29a8880-b93a-4e62-9bc8-46a1060857fd.png)
