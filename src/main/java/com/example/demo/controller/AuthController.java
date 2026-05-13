package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // 担当者: A
    // 目的: ユーザー登録画面の表示
    // 補足: 今後POSTメソッド(登録処理)の追加が必要です。
    //      入力値のバリデーション（@Validatedなど）と、DBへのINSERT処理を実装してください。
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // 担当者: A
    // 目的: ログイン画面の表示
    // 補足: Spring Securityなどは使わず、セッションでログイン状態を管理する想定です。
    //      今後POSTメソッド(ログイン処理)の追加と、DBのユーザー情報との照合処理が必要です。
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 担当者: B
    // 目的: アカウント情報画面の表示
    // 補足: セッションからログインユーザー情報を取得し、画面に渡す処理が今後必要です。
    //      必要に応じて、ユーザー情報の更新（編集機能）への導線も検討してください。
    @GetMapping("/account")
    public String account() {
        return "account";
    }
}
