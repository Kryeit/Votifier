<p align="center">
  <img width="200" src="https://github.com/Kryeit/Votifier/blob/1.20.1/master/src/main/resources/assets/votifier/icon.png">
</p>

<h1 align="center">Votifier for Fabric<br>
	<a href="https://legacy.curseforge.com/minecraft/mc-mods/fabricvotifier/files"><img src="https://cf.way2muchnoise.eu/versions/fabricvotifier.svg" alt="Supported Versions"></a>
	<a href="https://github.com/Kryeit/Votifier/LICENSE"><img src="https://img.shields.io/github/license/Creators-of-Create/Create?style=flat&color=900c3f" alt="License"></a>
	<a href="https://www.curseforge.com/minecraft/mc-mods/fabricvotifier"><img src="http://cf.way2muchnoise.eu/fabricvotifier.svg" alt="CF"></a>
    <a href="https://modrinth.com/mod/votifier"><img src="https://img.shields.io/modrinth/dt/votifier?logo=modrinth&label=&suffix=%20&style=flat&color=242629&labelColor=5ca424&logoColor=1c1c1c" alt="Modrinth"></a>
    <br><br>
</h1>

It's a really simple mod:
- Creates public & private keys inside `/mods/votifier/` folder
- Creates a votifier.json inside `/config/votifier/` folder

This votifier.json is:
```json
{
  "host": "0.0.0.0",
  "port": "8192",
  "debug": false,
  "command-after-voting": "/give %player% diamond 1"
}
```

To set up the Votifier:
1) Set "host" to your server IP
2) Fill the configuration on the Voting site, adding the PUBLIC key, NOT the PRIVATE.
3) Test with [MineStatus Votifier Tester](https://minestatus.net/tools/votifier)

Leave "command-after-voting" like `"command-after-voting": ""` to not run any command.
