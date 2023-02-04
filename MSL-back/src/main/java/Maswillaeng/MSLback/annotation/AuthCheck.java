package Maswillaeng.MSLback.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthCheck {
    Role role();

    enum Role {
        USER(1),
        ADMIN(2);

        private final int level;

        Role(int level) {
            this.level = level;
        }

        // this < role
        public static boolean greaterThan(Role left, String target){
            return false;
        }

    }
}
