package com.test.auth;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.stereotype.Component;

@Component
public class JwtProducer {

    public String createToken(String userName) {
        try {
            Date expireTime = new Date();
            expireTime.setTime(expireTime.getTime() + 600000l);

            /*
             * 署名に使うアルゴリズムインスタンスの作成
             * param...secret:任意の文字列の暗号キー。アルゴリズムの作成と、トークンのデコードで使う。
             */
            Algorithm algorithm = Algorithm.HMAC256("secret");

            /* 
             * JWT作成...クレーム(JSONのキーと値のペア)や有効期限などを設定
             */
            String token = JWT.create()
                    .withIssuer("auth0") //トークン発行者情報
                    .withSubject("any token name") //トークンの主体
                    .withClaim("userName",userName)
                    .withExpiresAt(expireTime) //有効期間終了時間
                    .sign(algorithm); //作成したJWTに指定アルゴリズムで署名

            return token;
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
            return null;
        }
    }

    public DecodedJWT verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");

            /*
             * トークン署名の検証クラスを作成
             */
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")//トークンから復元したクレーム値が同じかをチェックする
                    .build();
            
            /*
             * 指定されたトークンに対して検証を実施
             * exception...
             *   署名が無効、トークンの有効期限が切れている、
             *   クレームに予想とは異なる値が含まれている、
             *   ヘッダーに記載されているアルゴリズムが、verifierで定義されたアルゴリズムと異なる
             */
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (JWTVerificationException exception){
            exception.printStackTrace();
            return null;
        }
    }
}
