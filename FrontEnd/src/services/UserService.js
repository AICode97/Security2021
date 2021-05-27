import axios from "axios"
import AbstractService from "./AbstractService"
import TokenService from "./TokenService"

const tokenService = new TokenService()

export default class UserService extends AbstractService {

    async register(data) {
        return await axios.post(this.constructUrl(''), data)
    }

    async isAdmin() {
        try {
            const response = await this.user()

            return response.data.roles.includes('admin')
        } catch (_) {
            return false
        }
    }

    async all() {
        const token = tokenService.getToken()

        return await axios.get(this.constructUrl('all'), {
            headers: {
                'x-access-token': token
            }
        })
    }

    async user() {
        const token = tokenService.getToken()
 
        return await axios.get(this.constructUrl('user'), {
            headers: {
                'x-access-token': token
            }
        })
    }

    namespace() {
        return 'users'
    }
}