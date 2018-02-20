### Oversumo execution instructions

This application requires a local installation of MySQL server in order to function properly.
After installation this project can be built using the gradle wrapper, using the `gradlew clean build` command.

The server can be started on `http://localhost:8080` by running `gradlew bootRun`. It has the following APIs

```
GET /api/loadData - Load data from the overwatch API
GET /api/heros - Hero list
GET /api/heros/{hero_id} - Hero data
GET /api/heros/{hero_id}/abilities - Hero ability list
GET /api/abilities/ - Ability list
GET /api/abilities/{ability_id} - Ability data
```