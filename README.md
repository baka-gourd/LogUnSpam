# LogUnSpam

Simply a mod to filter logs that you don't want.

Filter rules live in `config/logunspam-common.toml` and are applied to every log sink the game uses:

| Option | What it matches |
| --- | --- |
| `commonFilterWords` | Lines containing any of these substrings |
| `regexpFilterWords` | Lines matching any of these regular expressions (the whole message must match) |
| `classFilterWords` | Lines emitted by these fully-qualified logger names |

## Development

```sh
./gradlew runClient     # launch a development client
./gradlew build         # produce build/libs/logunspam-<version>.jar
```

Requires JDK 21.
