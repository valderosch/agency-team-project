export class UserSaveRequest {
    constructor(
        public firstName: string, 
        public lastName: string,
        public email: string,
        public password: string,
        public phoneNumber: string,
        public locationId: string
    ) {}
}