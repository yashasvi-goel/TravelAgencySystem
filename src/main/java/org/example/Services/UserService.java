package org.example.Services;

import lombok.RequiredArgsConstructor;
import org.example.db.Storage;
import org.example.models.User;

import java.util.Objects;

@RequiredArgsConstructor
public class UserService {
    Storage storage;

    public UserService(Storage storage) {
        this.storage = storage;
    }

    public User CreateUser(User user){
        return storage.AddUser(user);
    }

    public User GetUser(Integer userId){
        return storage.GetUser(userId);
    }

    public boolean CheckBalance(Integer userId, Integer amount) {
        User user = storage.GetUser(userId);
        if(Objects.isNull(user)){
            System.out.println("User doesn't exist with Id: "+userId.toString());
            return false;
        }
        if(user.getBalance()<amount){
            System.out.println("The user doesn't have enough funds");
            return false;
        }
        return storage.UpdateUser(user);
    }
    public boolean UpdateBalance(Integer userId, Integer amount) {
        User user = storage.GetUser(userId);
        if(Objects.isNull(user)){
            System.out.println("User doesn't exist with Id: "+userId.toString());
            return false;
        }
        if(user.getBalance()<amount){
            System.out.println("The user doesn't have enough funds");
            return false;
        }
        user.setBalance(user.getBalance()-amount);
        return storage.UpdateUser(user);
    }
}
