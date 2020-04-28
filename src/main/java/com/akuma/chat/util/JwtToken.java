package com.akuma.chat.util;

import com.akuma.chat.model.entity.TokenInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Akuma
 * @date 2020/4/27 22:24
 */
public class JwtToken {

    // 构建头部信息
    private static Map<String, Object> HEAD_MAP = new HashMap<String, Object>();
    static {
        //        {
        //                "alg": "HS256",
        //                "typ": "JWT"
        //        }
        HEAD_MAP.put("alg", "HS256");
        HEAD_MAP.put("typ", "JWT");
    }

    // 构建密钥信息
    private static Algorithm ALGORITHM = Algorithm.HMAC256("secret");

    // JWT签发者
    private static String ISSUER = "server";
    // 过期时间
    private static long EXPIRATION_TIME = 1000*60*10; // 十分钟

    /**
     * 获取token
     * @param id 用户id
     * @param name 用户名称
     * @return token
     */
    public static String getToken(String id,String name,boolean isAdmin){
        Date now = new Date();

        String token = JWT.create()
                .withHeader(HEAD_MAP)

                /*设置 载荷 Payload*/
                //        {
                //                "sub": "this is token subject",      // 签名的主题
                //                "aud": "PWA",                     // 签名的观众 也可以理解谁接受签名的
                //                "name": "JabinGP",                // 自定义的信息
                //                "id":"20172005900",
                //                "iss": "service",                 // 签名是有谁生成 例如 服务器
                //                "exp": 1562612349,                // 签名过期的时间
                //                "iat": 1562611749                 // 生成签名的时间
                //        }
                .withClaim("name",name)
                .withClaim("id", id)
                .withClaim("admin",isAdmin)
                .withIssuer(ISSUER)//签名是有谁生成 例如 服务器

                .withSubject("login token")//签名的主题
                //.withNotBefore(new Date())//该jwt都是不可用的时间

                .withAudience("PWA")//签名的观众 也可以理解谁接受签名的
                .withIssuedAt(new Date()) //生成签名的时间
                .withExpiresAt(new Date(now.getTime()+EXPIRATION_TIME))//签名过期的时间,10分钟后


                /*签名 Signature */
                .sign(ALGORITHM);
        return token;
    }

    public static TokenInfo getTokenInfo(String token){

        // 构建验证对象
        JWTVerifier verifier = JWT.require(ALGORITHM)
                .withIssuer(ISSUER)
                .build();

        // 验证token是否有效
        DecodedJWT jwt = verifier.verify(token);

        // 获取Jwt中的信息
        Map<String, Claim> claims = jwt.getClaims();

        // 构建token信息实体
        TokenInfo tokenInfo = new  TokenInfo();
        tokenInfo.setSub(claims.get("sub").asString());
        tokenInfo.setAud(claims.get("aud").asString());

        tokenInfo.setIss(claims.get("iss").asString());
        tokenInfo.setName(claims.get("name").asString());
        tokenInfo.setId(claims.get("id").asString());
        tokenInfo.setIat(claims.get("iat").asLong());
        tokenInfo.setExp(claims.get("exp").asLong());
        tokenInfo.setAdmin(claims.get("admin").asBoolean());
        // 遍历map返回信息
//        for(Map.Entry<String, Claim> entry : claims.entrySet()){
//            tokenInfo += entry.getKey()+"=";
//
//            switch (entry.getKey()){ // 对不同键，将claim转换成不同的类型
//
//                case "exp":
//                case "iat":
//                    tokenInfo += entry.getValue().asLong();
//                    break;
//                default:
//                    tokenInfo += entry.getValue().asString();
//
//            }
//            tokenInfo += ",";
//        }

        return tokenInfo;
    }
}
