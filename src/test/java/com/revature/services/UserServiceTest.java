package com.revature.services;

import com.revature.daos.UserDAO;
import com.revature.dtos.requests.LoginRequest;
import com.revature.dtos.requests.NewUserRequest;
import com.revature.dtos.requests.admin.DeleteUserRequest;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.utils.custom_exceptions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.xml.bind.DatatypeConverter;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Test
    public void test_validateUsername_givenCorrectUsername() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String username = "christhewizard";
        UserService.validateUsername(username);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_validateUsername_givenTooShortUsername() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String username = "k";
        UserService.validateUsername(username);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_validateUsername_givenTooLongUsername() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String username = "ChrisTheAlmightyCoderExtraordinar";
        UserService.validateUsername(username);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_validateUsername_givenEmptyUsername() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String username = "";
        UserService.validateUsername(username);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void test_validateUsername_givenNull() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String username = null;
        UserService.validateUsername(username);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_validateUsername_givenInvalidEmail() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String username = "a@gmail.c";
        UserService.validateUsername(username);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_validateUsername_givenTooLongEmail() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String username = "DaenerysStormbornOfHouseTargaryenTheFirstOfHerNameQueenOfTheAndalsandTheFirstMenProtectorOfTheSevenKingdomsTheMotherOfDragonsTheKhaleesiOfTheGreatGrassSeaTheUnburntTheBreakerOfChains@gmail.com";
        UserService.validateUsername(username);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_validateUsername_startingWithUnderscore() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String username = "_bduong0929";
        UserService.validateUsername(username);
    }

    @Test
    public void test_validatePassword_givenCorrectPassword() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String password = "password";
        UserService.validatePassword(password);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_validatePassword_givenTooShortPassword() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String password = "pw";
        UserService.validatePassword(password);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_validatePassword_givenTooLongPassword() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String password = "CorrectHorseBatteryStapleCorrectHorseBatteryStapleCorrectHorseBatteryStapleCorrectHorseBatteryStaple";
        UserService.validatePassword(password);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_validatePassword_givenPasswordWithWrongCharacters() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String password = "password~";
        UserService.validatePassword(password);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void test_validatePassword_givenNull() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String password = null;
        UserService.validatePassword(password);
    }

    @Test(expected = InvalidRequestException.class)
    public void test_validatePassword_givenEmptyString() {
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String password = "";
        UserService.validatePassword(password);
    }

    @Test
    public void test_registerUser_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        NewUserRequest request = mock(NewUserRequest.class);
        when(request.getUsername()).thenReturn("christhewizard");
        when(request.getPassword()).thenReturn("fnord");
        when(request.getEmail()).thenReturn("christhewizard@gmail.com");
        when(request.getGiven_name()).thenReturn("Chris");
        when(request.getSurname()).thenReturn("The Wizard");
        when(request.getRole()).thenReturn("Employee");
        UserService.registerUser(request);
        verify(mockDAO, times(1)).save(any());
    }

    @Test
    public void test_getUserByUsername_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String username = "christhewizard";
        User mockUser = mock(User.class);
        when(mockDAO.getUserByUsername(any())).thenReturn(mockUser);
        assertNotNull(UserService.getUserByUsername(username));
        verify(mockDAO, times(1)).getUserByUsername(any());
    }

    @Test
    public void test_checkAvailableUsername_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String username = "christhewizard";
        when(mockDAO.isUsernameAvailable(any())).thenReturn(false);
        UserService.checkAvailableUsername(username);
        verify(mockDAO, times(1)).isUsernameAvailable(any());
    }

    @Test(expected = ResourceConflictException.class)
    public void test_checkAvailableUsername_Fail(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        String username = "christhewizard";
        when(mockDAO.isUsernameAvailable(any())).thenReturn(true);
        UserService.checkAvailableUsername(username);
        verify(mockDAO, times(1)).isUsernameAvailable(any());
    }

    @Test
    public void test_hashPassword_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        char[] password = "password".toCharArray();
        byte[] salt = DatatypeConverter.parseHexBinary(UUID.randomUUID().toString().replace("-",""));
        UserService.hashPassword(password, salt);
    }

    @Test (expected = AuthenticationException.class)
    public void test_validateLogin_Fail(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        User mockUser = mock(User.class);
        when(mockUser.getPassword()).thenReturn("fnord");
        when(mockDAO.getUserByUsername(any())).thenReturn(mockUser);
        LoginRequest request = mock(LoginRequest.class);
        when(request.getUsername()).thenReturn("christhewizard");
        when(request.getPassword()).thenReturn("fnord");
        assertNotNull(UserService.validateLogin(request));
        verify(mockDAO, times(1)).getUserByUsername(any());
    }

    @Test
    public void test_getAllUsers_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        UserService.getAllUsers();
        verify(mockDAO, times(1)).getAll();
    }

    @Test
    public void test_getAllActiveUsers_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        UserService.getAllActiveUsers();
        verify(mockDAO, times(1)).getAllActiveUsers();
    }

    @Test
    public void test_getAllInactiveUsers_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        UserService.getAllInactiveUsers();
        verify(mockDAO, times(1)).getAllInactiveUsers();
    }

    @Test
    public void test_updateUser_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        User mockUser = mock(User.class);
        UserService.updateUser(mockUser);
        verify(mockDAO, times(1)).update(any());
    }

    @Test
    public void test_activateUser_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        User mockUser = mock(User.class);
        String username = "christhewizard";
        when(mockUser.isActive()).thenReturn(false);
        when(mockDAO.getUserByUsername(any())).thenReturn(mockUser);
        UserService.activateUser(username);
        verify(mockDAO, times(1)).update(any());
    }

    @Test (expected = BadRequestException.class)
    public void test_activateUser_Fail(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        User mockUser = mock(User.class);
        String username = "christhewizard";
        when(mockUser.isActive()).thenReturn(true);
        when(mockDAO.getUserByUsername(any())).thenReturn(mockUser);
        UserService.activateUser(username);
        verify(mockDAO, times(1)).update(any());
    }

    @Test
    public void test_deactivateUser_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        User mockUser = mock(User.class);
        String username = "christhewizard";
        when(mockDAO.getUserByUsername(any())).thenReturn(mockUser);
        when(mockUser.isActive()).thenReturn(true);
        UserService.deactivateUser(username);
        verify(mockDAO, times(1)).update(any());
    }

    @Test (expected = BadRequestException.class)
    public void test_deactivateUser_Fail(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        User mockUser = mock(User.class);
        String username = "christhewizard";
        when(mockDAO.getUserByUsername(any())).thenReturn(mockUser);
        when(mockUser.isActive()).thenReturn(false);
        UserService.deactivateUser(username);
        verify(mockDAO, times(1)).update(any());
    }

    @Test
    public void test_deleteUser_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        DeleteUserRequest mockRequest = mock(DeleteUserRequest.class);
        when(mockRequest.getUsername()).thenReturn("christhewizard");
        User mockUser = mock(User.class);
        when(mockDAO.getUserByUsername(any())).thenReturn(mockUser);
        UserService.deleteUser(mockRequest);
        verify(mockDAO, times(1)).delete(any());
    }

    @Test
    public void test_changePassword_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        User mockUser = mock(User.class);
        when(mockDAO.getUserByUsername(any())).thenReturn(mockUser);
        String username = "christhewizard";
        String password = "password";
        UserService.changePassword(username, password);
        verify(mockDAO, times(1)).update(any());
    }

    @Test
    public void test_verifyUserRole_Succeed(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        User mockUser = mock(User.class);
        when(mockUser.getRole()).thenReturn(UserRole.ADMIN);
        UserService.verifyUserRole(mockUser, UserRole.ADMIN);
    }

    @Test (expected = ForbiddenException.class)
    public void test_verifyUserRole_Fail(){
        UserDAO mockDAO = mock(UserDAO.class);
        new UserService(mockDAO);
        User mockUser = mock(User.class);
        when(mockUser.getRole()).thenReturn(UserRole.EMPLOYEE);
        UserService.verifyUserRole(mockUser, UserRole.ADMIN);
    }
}
