package com.miracle.sport.community.bean;

import java.util.List;

/**
 * Created by Michael on 2018/10/30 21:29 (星期二)
 */
public class PostDetailBean {


    /**
     * id : 2
     * title : 趁热吃人
     * content : 那个非常非常冯v给他 发的东方给
     * thumb : ["http://xiaozhuang.988lhkj.com/uploads/20181103/846facc98a001d256dff45588ea908d2.jpg","http://xiaozhuang.988lhkj.com/uploads/20181103/a5aafeb2a15a81cd22795de69dec4954.jpg","http://xiaozhuang.988lhkj.com/uploads/20181103/f5ff9b7f9db461ccb6487245c16e848a.jpg"]
     * click_num : 0
     * comment_num : 4
     * class_id : 16
     * add_time : null
     * sq_name : 双色球预测
     * nickname : Michael
     * user_id : 12
     * click : 0
     * comment : [{"comment_id":5,"content":"范冰冰他不敢半天","add_time":"2018-11-03 20:50:37","nickname":"Michael","user_id":12,"img":"http://9.988lhkj.com/app/imgcom.miracle/10//M6UqcyAR2VQEPAJ34d0y17eapKJQzVDpYDOcpTml.jpeg","create_id":2,"comment_click_num":0,"click":0,"child":[],"child_count":0},{"comment_id":4,"content":"给不给吧过","add_time":"2018-11-03 20:50:26","nickname":"Michael","user_id":12,"img":"http://9.988lhkj.com/app/imgcom.miracle/10//M6UqcyAR2VQEPAJ34d0y17eapKJQzVDpYDOcpTml.jpeg","create_id":2,"comment_click_num":0,"click":0,"child":[],"child_count":0},{"comment_id":3,"content":"或许uu","add_time":"2018-11-03 20:48:43","nickname":"Michael","user_id":12,"img":"http://9.988lhkj.com/app/imgcom.miracle/10//M6UqcyAR2VQEPAJ34d0y17eapKJQzVDpYDOcpTml.jpeg","create_id":2,"comment_click_num":0,"click":0,"child":[],"child_count":0},{"comment_id":2,"content":"或许uu","add_time":"2018-11-03 20:47:48","nickname":"Michael","user_id":12,"img":"http://9.988lhkj.com/app/imgcom.miracle/10//M6UqcyAR2VQEPAJ34d0y17eapKJQzVDpYDOcpTml.jpeg","create_id":2,"comment_click_num":0,"click":0,"child":[{"content":"或许uu","add_time":"2018-11-03 20:47:48","nickname":"Michael","user_id":12,"to_name":null,"to_user_id":null},{"content":"或许uu","add_time":"2018-11-03 20:48:43","nickname":"Michael","user_id":12,"to_name":null,"to_user_id":null},{"content":"给不给吧过","add_time":"2018-11-03 20:50:26","nickname":"Michael","user_id":12,"to_name":null,"to_user_id":null},{"content":"范冰冰他不敢半天","add_time":"2018-11-03 20:50:37","nickname":"Michael","user_id":12,"to_name":null,"to_user_id":null}],"child_count":4}]
     */

    private int id;
    private String title;
    private String content;
    private int click_num;
    private int comment_num;
    private int class_id;
    private Object add_time;
    private String sq_name;
    private String nickname;
    private int user_id;
    private int click;
    private List<String> thumb;
    private List<CommentBean> comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getClick_num() {
        return click_num;
    }

    public void setClick_num(int click_num) {
        this.click_num = click_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public Object getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Object add_time) {
        this.add_time = add_time;
    }

    public String getSq_name() {
        return sq_name;
    }

    public void setSq_name(String sq_name) {
        this.sq_name = sq_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public List<String> getThumb() {
        return thumb;
    }

    public void setThumb(List<String> thumb) {
        this.thumb = thumb;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class CommentBean {
        /**
         * comment_id : 5
         * content : 范冰冰他不敢半天
         * add_time : 2018-11-03 20:50:37
         * nickname : Michael
         * user_id : 12
         * img : http://9.988lhkj.com/app/imgcom.miracle/10//M6UqcyAR2VQEPAJ34d0y17eapKJQzVDpYDOcpTml.jpeg
         * create_id : 2
         * comment_click_num : 0
         * click : 0
         * child : []
         * child_count : 0
         */

        private int comment_id;
        private String content;
        private String add_time;
        private String nickname;
        private int user_id;
        private String img;
        private int create_id;
        private int comment_click_num;
        private int click;
        private int child_count;
        private List<?> child;

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getCreate_id() {
            return create_id;
        }

        public void setCreate_id(int create_id) {
            this.create_id = create_id;
        }

        public int getComment_click_num() {
            return comment_click_num;
        }

        public void setComment_click_num(int comment_click_num) {
            this.comment_click_num = comment_click_num;
        }

        public int getClick() {
            return click;
        }

        public void setClick(int click) {
            this.click = click;
        }

        public int getChild_count() {
            return child_count;
        }

        public void setChild_count(int child_count) {
            this.child_count = child_count;
        }

        public List<?> getChild() {
            return child;
        }

        public void setChild(List<?> child) {
            this.child = child;
        }
    }
}
