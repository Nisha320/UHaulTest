package com.project.uhaultest.util;

import com.project.uhaultest.model.posts.Post;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Util {

    /**
     * Posts arranged in ascending order
     *
     * @param postList list of posts
     * @return sorted post list
     */
    public static List<Post> ascendingOrderPosts(List<Post> postList) {
        Collections.sort(postList, new Comparator<Post>() {
            @Override
            public int compare(final Post post1, final Post post2) {
                return post1.getTitle().compareTo(post2.getTitle());
            }
        });
        return postList;
    }

    /**
     * Posts arranged in descending order
     *
     * @param postList list of posts
     * @return sorted post list
     */
    public static List<Post> descendingOrderPosts(List<Post> postList) {
        Collections.sort(postList, new Comparator<Post>() {
            @Override
            public int compare(final Post post1, final Post post2) {
                return post2.getTitle().compareTo(post1.getTitle());
            }
        });
        return postList;
    }
}
