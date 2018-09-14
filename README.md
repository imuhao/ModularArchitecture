# ModularArchitecture


#### 架构模型

![](http://muhao.qiniudn.com/15369111265834.jpg)

在这个程序中我将整个应用程序分成了若干个模块,每个模块负责的逻辑如下:
|模块名称|包含逻辑|
| --- |  --- |
| Module_app | 整个 App 的壳,负责管理各个业务组件,打包 Apk, 混淆等,没有具体业务功能 |
| Module_main | 业务组件,App 启动页面,登录注册页面都在这个模块中负责 |
| Lib_common | 基础库模块(网络请求,图片加载),公用类,权限,全局的style 等 所有模块都依赖这个模块|

#### 模块化流程
1. 开发时模块之间模式的转换
为了区别当前模块要执行的操作,在`gradle.properties`中添加`isModule`属性,根据这个属性的值来区分模块的操作,`Module`的` build.gradle`代码如下:

```
if (isModule.toBoolean()) {
  apply plugin: 'com.android.application'
} else {
  apply plugin: 'com.android.library'
}

```

2. 模块之间` AndroidManifest`合并问题
在模块的目录下我们创建了一个这个模块作为独立应用的` AndroidManifest`文件
![](http://muhao.qiniudn.com/15369126976425.jpg)
然后在` build.gradle`文件中指定文件路径

```
  sourceSets {
    main {
      if (isModule.toBoolean()) {
        manifest.srcFile 'src/main/module/AndroidManifest.xml'
      } else {
        manifest.srcFile 'src/main/AndroidManifest.xml'
        //集成开发模式下排除debug文件夹中的所有Java文件
        java {
          exclude 'debug/**'
        }
      }
    }
  }
```

3. 模块之间调用和通信
在`lib_common`中添加了 `ARouter`作为整个项目的路由框架,在多个模块中使用 `ARouter` 时,需要在每个模块下面加入`annotationProcessorOptions`操作,并且在每个模块下面都要加入 `ARouter` 的注解处理器`annotationProcessor deps.arouter_compiler`
4. 模块之间资源名冲突
为了避免模块中的资源名冲突,我们可以在` build.gradle`中添加下面的代码对资源名指定前缀

```
defaultConfig {
   ...
   resourcePrefix "news_"
   ...
}
```
6. 模块化混淆方案
在模块化中,混淆我们只需要在壳程序中进行处理,不需要在模块中进行处理.

