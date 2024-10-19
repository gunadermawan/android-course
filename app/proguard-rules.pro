
-keep class androidx.compose.** {*;}
-keep class kotlin.Metadata {*;}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
}

-keep class com.gun.course.ComposeActivity {*;}

-keep class androidx.lifecycle.ViewModel {*;}

-keep class retrofit2.** {*;}
-keep interface retrofit2.** {*;}
-keepattributes Signature