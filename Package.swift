// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "GrowthBook",
    platforms: [
        .iOS(.v12)
    ],
    products: [
        .library(
            name: "GrowthBook",
            targets: ["GrowthBook"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "GrowthBook",
            url: "https://github.com/parimatch-tech/growthbook-kotlin/releases/download/1.2.10/GrowthBook.zip",
            checksum: "dcab0d1eea165bd798b8d4c31eb9e915304ecb10f8d239afd4e86bf8365023d7"
        ),
    ]
)
