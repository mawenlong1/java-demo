package com.mwl.beanwrapper;

public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
               "name='" + name + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return ((User) obj).getName() == name;
        }
        return super.equals(obj);
    }
}
