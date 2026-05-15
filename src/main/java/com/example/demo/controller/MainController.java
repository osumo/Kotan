package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 担当者: B
    // 目的: ルートディレクトリ兼room用ページの表示
    // 補足: 初期表示に必要なデータ(ユーザーの部屋情報など)をModelに詰める処理が今後必要です。
    //      ログイン状態をチェックし、未ログインならログイン画面へリダイレクトする処理も考慮してください。
    @GetMapping("/")
   public String index(HttpSession session) {
        if (session.getAttribute("loginUser") != null) {
            return "index";
        }
        return "redirect:/login";
    }

    // 担当者: C
    // 目的: gardenページの表示
    // 補足: 庭に関する情報をDBから取得して画面に表示する処理が今後必要です。
    //      将来的にはアイテムの配置や成長度合いなどの動的なデータの受け渡しを想定して実装を進めてください。
    @GetMapping("/garden")
    public String garden(HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
        return "garden";
    }

    // 担当者: C
    // 目的: aquariumページの表示
    // 補足: 水族館(水槽)に関する情報をDBから取得して画面に表示する処理が今後必要です。
    //      魚の種類や数など、ユーザーごとの状態をMyBatis経由で取得できるように準備してください。
    @GetMapping("/aquarium")
    public String aquarium(HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
        return "aquarium";
    }

    // 担当者: C
    // 目的: farmページの表示
    // 補足: 農場に関する情報をDBから取得して画面に表示する処理が今後必要です。
    //      作物の状態や収穫などのユーザーアクションに応じたデータの更新も後々必要になります。
    @GetMapping("/farm")
    public String farm(HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
        return "farm";
    }
}
