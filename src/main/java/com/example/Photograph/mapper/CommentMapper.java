package com.example.Photograph.mapper;

import com.example.Photograph.dto.CommentDto;
import com.example.Photograph.model.Comment;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Locale;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Mapping(target="created", expression = "java(getTimeAgo(comment, locale))")
    @Mapping(target = "username", source="comment.user.username")
    public abstract CommentDto commentToDto(Comment comment, Locale locale);

    public String getTimeAgo(Comment comment, Locale locale){
        TimeAgoMessages messages = new TimeAgoMessages.Builder().withLocale(locale).build();
        return TimeAgo.using(comment.getCreated().getTime(), messages);
    }
}
