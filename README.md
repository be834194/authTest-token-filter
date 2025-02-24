# authTest-token-filter
独自に実装したフィルタークラスを用いて、SpringでAPI認証を実現したサンプルです。

## ログイン
```
POST http://localhost:8080/login
{
  "username": "...",
  "password": ""
}
```
![image](https://github.com/user-attachments/assets/0350a2b0-f70e-4fe6-86bd-8addaf104935)

## JWTを使ったAPI実行

### 成功
![image](https://github.com/user-attachments/assets/5a0c13dd-f98f-4ef1-8c9b-81bed41a669c)

### 失敗
![image](https://github.com/user-attachments/assets/905e87fd-716c-4543-968e-c551ba113c36)
