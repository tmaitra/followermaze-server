package com.followermaze.event;

import java.util.Optional;
/**
 * This class will parse event 
 *     
    | Payload    | Sequence #| Type         | From User Id | To User Id |
    |------------|-----------|--------------|--------------|------------|
    |666|F|60|50 | 666       | Follow       | 60           | 50         |
    |1|U|12|9    | 1         | Unfollow     | 12           | 9          |
    |542532|B    | 542532    | Broadcast    | -            | -          |
    |43|P|32|56  | 43        | Private Msg  | 2            | 56         |
    |634|S|32    | 634       | Status Update| 32           | -          |
     
 * @author tirtha
 *
 */
public class EventParser {

    public Optional<? extends BaseEvent> parse(String raw) {

        final String[] fields = raw.split("\\|");

        if(fields.length < 2) {
           return Optional.empty();
        }

        final char type = fields[1].charAt(0);
        final int sequence = Integer.parseInt(fields[0]);

        String from = null;
        String to = null;

        if (fields.length > 2) {
            from = fields[2];
        }

        if (fields.length > 3) {
            to = fields[3];
        }

        switch(type) {
            case 'B': return Optional.of(new BroadcastEvent(raw, sequence));

            case 'S': return Optional.of(new StatusUpdateEvent(raw, sequence, from));

            case 'F': return Optional.of(new FollowEvent(raw, sequence, from, to));

            case 'U': return Optional.of(new UnfollowEvent(raw, sequence, from, to));

            case 'P': return Optional.of(new PrivateMessageEvent(raw, sequence, from, to));

            default: return Optional.empty();
        }
    }
}
