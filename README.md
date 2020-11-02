# navigator
### Utility class to help Android navigation (Activities and Fragments)


To import the library add the following code in your root build.gradle at the end of repositories:

```gradle
allprojects {
    repositories {
    ...
    maven { url 'https://jitpack.io' }
    }
}
```

Next add the dependency to the build.gradle of your module:

```gradle
dependencies {
    implementation 'com.github.santukis:navigator:1.0.0@aar'
}
```