package com.techweb.onlinetest.admin.poc;

import java.util.List;

/**
 * Created by manishsanger on 14/10/16.
 */
public class ProductModel {
        private String name;
        private List<ProductModel> children;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public List<ProductModel> getChildren() {
            return children;
        }
        public void setChildren(List<ProductModel> children) {
            this.children = children;
        }
        public String toString(){
            return "name: "+name + ", children: {"+children+"}";
        }
}
