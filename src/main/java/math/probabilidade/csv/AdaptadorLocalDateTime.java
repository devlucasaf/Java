package math.probabilidade.csv;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class AdaptadorLocalDateTime implements JsonSerializer<LocalDateTime> {

    @Override
    public JsonElement serialize(LocalDateTime data, Type tipo, JsonSerializationContext contexto) {
        return new JsonPrimitive(data.toString());
    }
}

