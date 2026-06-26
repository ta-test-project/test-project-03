package com.softserve.academy.model;

@lombok.AllArgsConstructor
@lombok.Getter
@lombok.Setter
public class User {
    private String email;
    private String name;
    private String password;

    private User(Builder builder) {
        this.email = builder.email;
        this.name = builder.name;
        this.password = builder.password;
    }

    @Override
    public String toString() {
        return "User{" + "email='" + email + '\'' + ", name='" + name + '\'' + '}';
    }


    public static class Builder {
        private String email = "valid.email@email.com";
        private String name = "ValidUsername";
        private String password = "ValidPass123!";

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}