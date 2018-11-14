export class SignUpInfo {
    name: string;
    login: string;
    email: string;
    role: string[];
    password: string;
    matchingPassword: string;

    constructor(name: string, login: string, email: string, password: string,matchingPassword: string) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
        this.matchingPassword= matchingPassword;
        this.role = ['user'];
    }
}
