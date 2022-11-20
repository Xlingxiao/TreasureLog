import CryptoJS from 'crypto-js'
import JSEncrypt from 'jsencrypt'

let defaultKey = "AAAAEmx4QERFU0tU";
let defaultIv = "b3BlbnNzaC1rZXkt";

export default {
    genAesKey(){
        defaultIv = crypto.randomUUID().replaceAll("-","").substring(0,16);
        defaultKey = crypto.randomUUID().replaceAll("-","").substring(0,16);
        let info = {
            key: this.encryptRSA(defaultKey),
            iv: this.encryptRSA(defaultIv)
        }
        return info;
    },
    getAesKey() {
        return {
            'key': defaultKey,
            'iv': defaultIv
        }
    },

    encrypt(word, selfKey, selfIv) {

        let keyStr = selfKey ? selfKey : defaultKey;
        let ivStr = selfIv ? selfIv : defaultIv;

        let key = CryptoJS.enc.Utf8.parse(keyStr);
        let iv = CryptoJS.enc.Utf8.parse(ivStr);
        let str = CryptoJS.enc.Utf8.parse(word);
        let encrypted = CryptoJS.AES.encrypt(str, key, { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
        return encrypted.toString();
    },
    decrypt(word, selfKey, selfIv) {
        let keyStr = selfKey ? selfKey : defaultKey;
        let ivStr = selfIv ? selfIv : defaultIv;

        let key = CryptoJS.enc.Utf8.parse(keyStr);
        let iv = CryptoJS.enc.Utf8.parse(ivStr);
        let str = CryptoJS.enc.Utf8.parse(word);
        let decrypted = CryptoJS.AES.decrypt(str, key, { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
        return decrypted.toString();
    },
    // RSA加密
    encryptRSA(words) {
        let pkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDmorb0FsMUdJwnKABOiKeSuEeU2Ulq0jXWPlGz2VhitO4pXqk61E92rbOnPnkt+niEs/KaReGngJlPHLtF4U2lRLIoqPOjC8Wlc8ogES3zNtpjYhanWUDSVmnuFumB6R7mZxJ1xVQYo0SULTlfWOcf+SrCwjPFdK3CvSwue8tPIwIDAQAB"
        var encrypt = new JSEncrypt();
        encrypt.setPrivateKey(pkey);
        let data = encrypt.encrypt(words);
        return data;
    }
}