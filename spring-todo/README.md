このデモを動かすためには PostgreSQL が必要です。

PostgreSQLの設定は以下の通りです。

* ユーザ名：restcrud
* パスワード：restcrud
* DB名：rest-crud
* ポート番号：5432

これらの設定は `src/main/resources/application.properties` にて設定されているため、必要に応じて任意の設定に変更ください。

PostgreSQL を起動
```bash
docker run --name quarkus_test -e POSTGRES_USER=restcrud -e POSTGRES_PASSWORD=restcrud -e POSTGRES_DB=rest-crud -p 5432:5432 postgres:latest
```

Quarkus を起動
```
mvn compile quarkus:dev
```

ブラウザで http://localhost/ へアクセス
