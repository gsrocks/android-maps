package com.gsrocks.locationmaps.core.geo

import com.gsrocks.locationmaps.core.model.Coordinates
import com.gsrocks.locationmaps.core.model.DirectionLeg
import com.gsrocks.locationmaps.core.model.DirectionRoute
import com.gsrocks.locationmaps.core.model.MapBounds

class FakeDirectionsDataSource : DirectionsDataSource {
    override suspend fun getDirectionsBetween(
        start: Coordinates,
        end: Coordinates
    ): DirectionRoute {
        return dummyRoute
    }

    companion object {
        private val dummyRoute = DirectionRoute(
            bounds = MapBounds(
                northeast = Coordinates(48.464069, 35.066867),
                southwest = Coordinates(48.425480, 35.029111)
            ),
            legs = listOf(
                DirectionLeg(
                    Coordinates(48.426198, 35.031108),
                    Coordinates(48.429483, 35.040115)
                ),
                DirectionLeg(
                    Coordinates(48.429483, 35.040115),
                    Coordinates(48.431278, 35.042387)
                ),
                DirectionLeg(
                    Coordinates(48.431278, 35.042387),
                    Coordinates(48.440566, 35.050501)
                ),
                DirectionLeg(
                    Coordinates(48.440566, 35.050501),
                    Coordinates(48.442759, 35.050733)
                ),
                DirectionLeg(
                    Coordinates(48.442759, 35.050733),
                    Coordinates(48.454690, 35.065412)
                ),
                DirectionLeg(
                    Coordinates(48.454690, 35.065412),
                    Coordinates(48.460056, 35.055117)
                ),
                DirectionLeg(
                    Coordinates(48.460056, 35.055117),
                    Coordinates(48.460718, 35.055859)
                ),
                DirectionLeg(
                    Coordinates(48.460718, 35.055859),
                    Coordinates(48.461359, 35.059157)
                ),
                DirectionLeg(
                    Coordinates(48.461359, 35.059157),
                    Coordinates(48.462538, 35.058465)
                ),
                DirectionLeg(
                    Coordinates(48.462538, 35.058465),
                    Coordinates(48.462606, 35.058579)
                )
            ),
            polylinePoints = listOf(
                Coordinates(48.426198, 35.031108),
                Coordinates(48.429483, 35.040115),
                Coordinates(48.431278, 35.042387),
                Coordinates(48.440566, 35.050501),
                Coordinates(48.442759, 35.050733),
                Coordinates(48.454690, 35.065412),
                Coordinates(48.460056, 35.055117),
                Coordinates(48.460718, 35.055859),
                Coordinates(48.461359, 35.059157),
                Coordinates(48.462538, 35.058465),
                Coordinates(48.462606, 35.058579)
            )
        )
    }
}
