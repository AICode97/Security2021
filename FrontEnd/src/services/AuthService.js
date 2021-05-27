import axios from "axios";
import AbstractService from "./AbstractService";
import TokenService from "./TokenService";

const tokenService = new TokenService()

export default class AuthService extends AbstractService {

    async login(username, password) {
        const response = await axios.post(this.constructUrl(''), {
            username,
            password
        })

        tokenService.setToken(response.data.token)
    }

    namespace() {
        return 'login'
    }
}