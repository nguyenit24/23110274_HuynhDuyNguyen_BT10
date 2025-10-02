
package vn.iot.service;

import vn.iot.enity.UserEntity;

public interface IUserService {

    UserEntity register(UserEntity user);

    UserEntity login(String username, String password);
}