// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "GrowthBookLight",
    platforms: [
        .iOS(.v12)
    ],
    products: [
        .library(
            name: "GrowthBookLight",
            targets: ["GrowthBookLight"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "GrowthBookLight",
            url: "https://github.com/parimatch-tech/growthbook-kotlin/raw/Poc/RemoveNetworkLayer/GrowthBookLight.xcframework.zip",
            checksum: "5e69bd2ecf26b12127d91af068d24115ec1ec92c28bb9fa93fe8becd10ba3f54"
        ),
    ]
)
