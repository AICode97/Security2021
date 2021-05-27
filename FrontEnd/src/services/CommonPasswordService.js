import axios from "axios";

export default class CommonPasswordService {

    async fetchCommonPasswords() {
        const words = await axios.get(process.env.REACT_APP_COMMON_PASSWORDS_URL)

        return words.data.split('\n')
    }

}