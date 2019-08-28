# 色々な Azure WebApps に Spring Boot アプリケーションをデプロイする方法

Azure WebApps は複数のOS（Windows、Linux）と複数のアプリケーションコンテナをサポートしています。それぞれのAzure WebAppsにデプロイする最小コードのサンプルです。

以下の組み合わせを想定しています

* Windows
  * Jetty (jar)
  * Tomcat 8.5 (war)

* Linux
  * JRE (jar)
  * Tomcat 8.5 (war)

最小サンプルは、JSON を返却する RestControllerです。

## デプロイ方法

### pom.xml（共通）

`azure-webapp-maven-plugin` を利用しています。Web Appsの構成は `pom.xml` に記述されています。`mvn azure-webapp:config` で再構成が可能です。また`configuration` 要素を削除してから実行すると、初期設定可能です。

最小限、各々の `pom.xml` の以下を書き換えてください。

`appName` 要素 は、 Azure WebApps のドメインです。Azure全体で一意な必要があるため、そこだけは書き換える必要があります。

`resourceGroup` や `appServicePlanName` また、`pricingTier` 、`region` は任意に書き換えればよいです。

各要素の詳細は、 [Maven Plugin for Azure App Service | Microsoft Docs](https://docs.microsoft.com/ja-jp/java/api/overview/azure/maven/azure-webapp-maven-plugin/readme?view=azure-java-stable) のリファレンスを参照のこと。

### サブスクリプション

`az` コマンドであらかじめAzureサブスクリプションにログインする必要があります。サブスクリプションが複数ある場合は、一覧の `IsDefault` が `True` なものに作られますので、デフォルトに注意してください。

```
az account login
az account list -o table 
```

## 実際の操作

### linux jre8

WebApps on Linux に Spring Boot の jar をデプロイするサンプルです。

```
mvn clean package azure-webapp:deploy
```

とAzure上にリソースが作成され、デプロイされます。リソースが作成済みの場合はそれが利用されます。ブラウザや curl でURLを叩くとJSONのレスポンスが取得できます。

```
curl https://foobar.azurewebsites.net/hello?name=world
```

### Windows jetty

WebApps on Windows に Spring Boot の jar をデプロイするサンプルです。コンテナがJettyになっています。

オペレーションはlinux jre8 と同様です。

### Linux Tomcat 8.5

WebApps on Linux に Spring Boot の war をデプロイするサンプルです。jar をデプロイする場合との差分を説明します。

`SpringBootServletInitializer` を継承します。これがないとデプロイは成功しますが起動しません。 war からSpring Bootアプリケーションを起動するときには必須です。それに加え`configure` メソッドをオーバーライドします（しなくても実行できたけど）。

また、`pom.xml` に `<packaging>war</packageing>` を定義します。詳細はサンプルを参照してください。


以下でデプロイします。spring boot plugin を削除するのが面倒だったので（ローカルでrunしたかったので）、リパッケージをスキップしてます。

```
mvn clean package azure-webapp:deploy -Dspring-boot.repackage.skip=true
```

サンプルでは `sample` コンテキストにデプロイするようになっています。ROOTコンテキストにデプロイしたい場合は、`configuration` 配下の以下の要素を削除してください。

```xml
<targetPath>${project.build.finalName}</targetPath>
```

curl を実行してレスポンスを確認します。

```
curl https://foobar.azurewebsites.net/sample/hello
```

Tomcatですとデプロイしてからレスポンスが返ってくるまで少し時間がかかりるみたいです。

## Windows Tomcat 8.5

Linux の場合と同じです。


以上