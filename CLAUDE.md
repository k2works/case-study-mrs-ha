# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

日本語を使う

## プロジェクト概要

会議室予約システム（MRS - Meeting Room System）のヘキサゴナルアーキテクチャ実装。Spring Boot 3.3.2を使用したJavaアプリケーション。

## 開発コマンド

### ビルドと実行
```bash
# Javaアプリケーションの起動
gradle bootRun

# ビルド
gradle build

# テスト実行
gradle test

# JaCoCo カバレッジレポート生成
gradle jacocoTestReport

# JIGドキュメント生成
gradle jigReports

# ジャーナル生成（Git履歴から）
gradle generateJournal
```

### フロントエンド開発
```bash
# 開発サーバー起動（Gulpを使用）
npm start

# ビルド
npm run build

# テスト
npm test

# コードフォーマット
npm run format

# ファイル監視
npm run watch

# ドキュメント生成
npm run docs
```

## アーキテクチャ

### ヘキサゴナルアーキテクチャ構成

```
src/main/java/mrs/
├── application/          # アプリケーション層
│   ├── domain/          # ドメインモデル
│   │   └── model/
│   │       ├── auth/    # 認証関連（User, Password, Role）
│   │       ├── reservation/ # 予約関連（Reservation, ReservableRoom）
│   │       └── room/    # 会議室関連（MeetingRoom）
│   ├── port/            # ポート定義
│   │   ├── in/          # 入力ポート（UseCase）
│   │   └── out/         # 出力ポート（永続化インターフェース）
│   └── service/         # ビジネスロジック実装
│       ├── auth/        # 認証サービス
│       ├── reservation/ # 予約サービス
│       └── room/        # 会議室サービス
├── infrastructure/       # インフラストラクチャ層
│   ├── in/              # 入力アダプタ
│   │   └── web/         # Spring MVC コントローラー
│   └── out/             # 出力アダプタ
│       └── persistence/ # JPA実装
└── common/              # 共通機能
    └── validation/      # カスタムバリデーション
```

### 主要技術スタック

- **フレームワーク**: Spring Boot 3.3.2, Spring Security, Spring Data JPA
- **ビュー**: Thymeleaf
- **データベース**: H2（開発）、PostgreSQL（本番）
- **マイグレーション**: Flyway
- **ビルドツール**: Gradle
- **テスト**: JUnit 5, Spring Boot Test
- **ドキュメント生成**: JIG (Java Instant-document Gazer)

### データベース設定

- 開発環境: H2インメモリデータベース（PostgreSQLモード）
- Flywayによる自動マイグレーション
- 開発用初期データあり（`/db/migration/dev/`）

### セキュリティ

- Spring Securityによる認証・認可
- フォームベースログイン実装
- Thymeleaf Spring Security統合

## テスト戦略

- 単体テスト: 各Service、Domainモデル
- 統合テスト: Controller、PersistenceAdapter
- テストカバレッジ: JaCoCoで計測