import {http} from "./http";

const API_URL = "/api/auth/";

class AuthService {

    register(username: string, email: string, password: string){
        return http.post(API_URL + "signup", {
            username,
            email,
            password
        });
    }
}

export default new AuthService();