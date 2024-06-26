{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils, ...}@inputs:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
        jdk22 = pkgs.stdenv.mkDerivation {
          nativeBuildInputs = [ pkgs.makeWrapper ];
          name = "jdk22-macos-x64";
          src = builtins.fetchTarball {
            url = "https://download.java.net/java/GA/jdk22/830ec9fcccef480bb3e73fb7ecafe059/36/GPL/openjdk-22_macos-x64_bin.tar.gz";
            sha256 = "13prfvyishlp8z5hd19pr1f20k66xipfm73hizbnzxvifv2rv7zy";
          };
          installPhase = ''
            mkdir -p $out
            cp -r * $out
            ln -s Contents/Home/bin $out/bin
          '';
        };
        java-lsp = pkgs.java-language-server.override {
          jdk = jdk22;
        };
        maven = pkgs.maven.override {
          jdk = jdk22;
        };
      in
      {
        devShell =
          pkgs.mkShell {
            JAVA_HOME="${jdk22}";
            buildInputs = [
              jdk22
              maven
              pkgs.google-java-format
              java-lsp
              pkgs.nixpkgs-fmt
            ];
          };
      }
  );
}
