export class SignUpInfo {
    login: string;
    email: string;
    role: string[];
    password: string;
    matchingPassword: string;

    constructor(login: string, email: string, password: string,matchingPassword: string) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.matchingPassword= matchingPassword;
        this.role = ['user'];
    }
}
