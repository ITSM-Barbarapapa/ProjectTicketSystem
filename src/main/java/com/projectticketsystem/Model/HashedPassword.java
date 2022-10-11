package com.projectticketsystem.Model;

public class HashedPassword
{
    private final byte[] hashedPassword;
    private final byte[] salt;

    public HashedPassword(byte[] hashedPassword, byte[] salt)
    {
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public byte[] getHashPassword()
    {
        return hashedPassword;
    }

    public byte[] getSalt()
    {
        return salt;
    }

}
