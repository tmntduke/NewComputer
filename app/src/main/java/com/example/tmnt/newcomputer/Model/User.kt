package com.example.tmnt.newcomputer.Model

/**
 * Created by tmnt on 2017/6/8.
 */

class User {

    /**
     * status : true
     * errorMsg :
     * User : {"id":"f2149a69-d36f-45bc-ad74-12220f0b117a","name":"张丽","roleCode":"ZW","groupId":"17","groupRoleCode":"WG","roleId":"6","password":"123456","job_num":"145","role_name":"装维人员","group_name":"网格A"}
     */

    var isStatus: Boolean = false
    var errorMsg: String? = null
    var user: UserBean? = null

    class UserBean {
        /**
         * id : f2149a69-d36f-45bc-ad74-12220f0b117a
         * name : 张丽
         * roleCode : ZW
         * groupId : 17
         * groupRoleCode : WG
         * roleId : 6
         * password : 123456
         * job_num : 145
         * role_name : 装维人员
         * group_name : 网格A
         */

        var id: String? = null
        var name: String? = null
        var roleCode: String? = null
        var groupId: String? = null
        var groupRoleCode: String? = null
        var roleId: String? = null
        var password: String? = null
        var job_num: String? = null
        var role_name: String? = null
        var group_name: String? = null

    }
}
