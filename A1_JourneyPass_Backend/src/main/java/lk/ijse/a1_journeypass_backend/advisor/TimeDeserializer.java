package lk.ijse.a1_journeypass_backend.advisor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeDeserializer extends JsonDeserializer<Time> {
    @Override
    public Time deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String timeString = p.getText().trim();
        if (timeString.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            java.util.Date date = sdf.parse(timeString);
            return new Time(date.getTime());
        } catch (ParseException e) {
            throw new IOException("Unable to parse time: " + timeString, e);
        }
    }
}