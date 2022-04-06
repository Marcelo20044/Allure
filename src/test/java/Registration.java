public class Registration {

    private Registration() {
    }

    public static RegistrationDto getUser(String status) {
        return new RegistrationDto(DataGenerator.getRandomLogin(), DataGenerator.getRandomPassword(), status);
    }

    public static RegistrationDto getRegisteredUser(String status) {
        var registeredUser = getUser(status);
        DataGenerator.sendRequest(registeredUser);
        return registeredUser;
    }
}



