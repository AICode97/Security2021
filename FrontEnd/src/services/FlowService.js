import axios from "axios";
import AbstractService from "./AbstractService";
import TokenService from "./TokenService"

const tokenService = new TokenService()


export default class FlowService extends AbstractService {

    async makeFlow(data) {
        const token = tokenService.getToken()
        
        return await axios.post(this.constructUrl(''), 
            data,
            {
                headers: {
                    'x-access-token': token
                }
            }
        )
    }

    namespace() {
        return 'flow'
    }
}



 