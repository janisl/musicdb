(function() {

    var app = angular.module( 'releases', [] );

    app.controller( 'ReleasesController', [ 'Release', function( Release ) {
        var ctrl = this;
        this.filterText = '';
        this.releases = [];

        this.getList = function() {
            ctrl.releases = Release.query();
        };

        this.delete = function( release ) {
            if ( confirm( "Are you sure you want to delete this release?" ) === true ) {
                release.$delete( {}, function() {
                    ctrl.getList();
                }, function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };

        this.getList();
    }]);

    app.controller( 'ReleaseAddController', [ 'Release', '$location', function( Release, $location ) {
        var ctrl = this;
        this.release = new Release();
        this.release.artists = [];
        this.release.tracks = [];

        this.submit = function() {
            for ( var i = 0; i < ctrl.release.artists.length; i++ ) {
                ctrl.release.artists[ i ].orderNumber = i + 1;
            }
            for ( var i = ctrl.release.tracks.length - 1; i >= 0; i-- ) {
                for ( var j = 0; j < ctrl.release.tracks[ i ].artists.length; j++ ) {
                    ctrl.release.tracks[ i ].artists[ j ].orderNumber = j + 1;
                }
            }
            ctrl.release.$save( {}, function() {
                $location.path( '/releases/' );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
        
        this.addArtist = function() {
            ctrl.release.artists.push( { orderNumber: ctrl.release.artists.length + 1 } );
        };
        
        this.removeArtist = function( releaseArtist ) {
            for ( var i = ctrl.release.artists.length - 1; i >= 0; i-- ) {
                if ( ctrl.release.artists[i] === releaseArtist ) {
                   ctrl.release.artists.splice( i, 1 );
                }
            }
        };
        
        this.artistMoveUp = function( releaseArtist ) {
            for ( var i = 1; i < ctrl.release.artists.length; i++ ) {
                if ( ctrl.release.artists[i] === releaseArtist ) {
                    var tmp = ctrl.release.artists[ i - 1 ];
                    ctrl.release.artists[ i - 1 ] = ctrl.release.artists[ i ];
                    ctrl.release.artists[ i ] = tmp;
                }
            }
        };
        
        this.artistMoveDown = function( releaseArtist ) {
            for ( var i = 0; i < ctrl.release.artists.length - 1; i++ ) {
                if ( ctrl.release.artists[i] === releaseArtist ) {
                    var tmp = ctrl.release.artists[ i + 1 ];
                    ctrl.release.artists[ i + 1 ] = ctrl.release.artists[ i ];
                    ctrl.release.artists[ i ] = tmp;
                }
            }
        };
        
        this.addTrack = function() {
            ctrl.release.tracks.push( { artists: [] } );
        };
        
        this.removeTrack = function( track ) {
            for ( var i = ctrl.release.tracks.length - 1; i >= 0; i-- ) {
                if ( ctrl.release.tracks[i] === track ) {
                   ctrl.release.tracks.splice( i, 1 );
                }
            }
        };
        
        this.addTrackArtist = function( track ) {
            track.artists.push( { orderNumber: track.artists.length + 1 } );
        };
        
        this.removeTrackArtist = function( track, trackArtist ) {
            for ( var i = track.artists.length - 1; i >= 0; i-- ) {
                if ( track.artists[i] === trackArtist ) {
                   track.artists.splice( i, 1 );
                }
            }
        };
        
        this.trackArtistMoveUp = function( track, trackArtist ) {
            for ( var i = 1; i < track.artists.length; i++ ) {
                if ( track.artists[i] === trackArtist ) {
                    var tmp = track.artists[ i - 1 ];
                    track.artists[ i - 1 ] = track.artists[ i ];
                    track.artists[ i ] = tmp;
                }
            }
        };
        
        this.trackArtistMoveDown = function( track, trackArtist ) {
            for ( var i = 0; i < track.artists.length - 1; i++ ) {
                if ( track.artists[i] === trackArtist ) {
                    var tmp = track.artists[ i + 1 ];
                    track.artists[ i + 1 ] = track.artists[ i ];
                    track.artists[ i ] = tmp;
                }
            }
        };

        this.addArtist();
    }]);

    app.controller( 'ReleaseEditController', [ 'Release', '$routeParams', '$location', function( Release, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.release = Release.get( { id: $routeParams.id } );

        this.submit = function() {
            for ( var i = 0; i < ctrl.release.artists.length; i++ ) {
                ctrl.release.artists[ i ].orderNumber = i + 1;
            }
            for ( var i = ctrl.release.tracks.length - 1; i >= 0; i-- ) {
                for ( var j = 0; j < ctrl.release.tracks[ i ].artists.length; j++ ) {
                    ctrl.release.tracks[ i ].artists[ j ].orderNumber = j + 1;
                }
            }
            ctrl.release.$update( {}, function() {
                $location.path( '/releases/' + ctrl.id );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
        
        this.addArtist = function() {
            ctrl.release.artists.push( { orderNumber: ctrl.release.artists.length + 1 } );
        };
        
        this.removeArtist = function( releaseArtist ) {
            for ( var i = ctrl.release.artists.length - 1; i >= 0; i-- ) {
                if ( ctrl.release.artists[i] === releaseArtist ) {
                    ctrl.release.artists.splice( i, 1 );
                }
            }
        };
        
        this.artistMoveUp = function( releaseArtist ) {
            for ( var i = 1; i < ctrl.release.artists.length; i++ ) {
                if ( ctrl.release.artists[i] === releaseArtist ) {
                    var tmp = ctrl.release.artists[ i - 1 ];
                    ctrl.release.artists[ i - 1 ] = ctrl.release.artists[ i ];
                    ctrl.release.artists[ i ] = tmp;
                }
            }
        };
        
        this.artistMoveDown = function( releaseArtist ) {
            for ( var i = 0; i < ctrl.release.artists.length - 1; i++ ) {
                if ( ctrl.release.artists[i] === releaseArtist ) {
                    var tmp = ctrl.release.artists[ i + 1 ];
                    ctrl.release.artists[ i + 1 ] = ctrl.release.artists[ i ];
                    ctrl.release.artists[ i ] = tmp;
                }
            }
        };
        
        this.addTrack = function() {
            ctrl.release.tracks.push( { artists: [] } );
        };
        
        this.removeTrack = function( track ) {
            for ( var i = ctrl.release.tracks.length - 1; i >= 0; i-- ) {
                if ( ctrl.release.tracks[i] === track ) {
                   ctrl.release.tracks.splice( i, 1 );
                }
            }
        };
        
        this.addTrackArtist = function( track ) {
            track.artists.push( { orderNumber: track.artists.length + 1 } );
        };
        
        this.removeTrackArtist = function( track, trackArtist ) {
            for ( var i = track.artists.length - 1; i >= 0; i-- ) {
                if ( track.artists[i] === trackArtist ) {
                    track.artists.splice( i, 1 );
                }
            }
        };
        
        this.trackArtistMoveUp = function( track, trackArtist ) {
            for ( var i = 1; i < track.artists.length; i++ ) {
                if ( track.artists[i] === trackArtist ) {
                    var tmp = track.artists[ i - 1 ];
                    track.artists[ i - 1 ] = track.artists[ i ];
                    track.artists[ i ] = tmp;
                }
            }
        };
        
        this.trackArtistMoveDown = function( track, trackArtist ) {
            for ( var i = 0; i < track.artists.length - 1; i++ ) {
                if ( track.artists[i] === trackArtist ) {
                    var tmp = track.artists[ i + 1 ];
                    track.artists[ i + 1 ] = track.artists[ i ];
                    track.artists[ i ] = tmp;
                }
            }
        };
        
        this.addTrackRemixer = function( track ) {
            track.remixers.push( {} );
        };
        
        this.removeTrackRemixer = function( track, trackRemixer ) {
            for ( var i = track.remixers.length - 1; i >= 0; i-- ) {
                if ( track.remixers[i] === trackRemixer ) {
                   track.remixers.splice( i, 1 );
                }
            }
        };
    }]);

    app.controller( 'ReleaseDetailsController', [ 'Release', '$routeParams', '$location', function( Release, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.release = Release.get( { id: $routeParams.id } );

        this.delete = function() {
            if ( confirm( "Are you sure you want to delete this release?" ) === true ) {
                ctrl.release.$delete( {}, function() {
                    $location.path( '/releases/' );
                }, function( httpResponse ) {
                    alert( 'Error ' + httpResponse );
                });
            }
        };

        this.createDir = function() {
            ctrl.release.$createDir( {}, function() {
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };

        this.moveTracks = function() {
            ctrl.release.$moveTracks( {}, function() {
                ctrl.release = Release.get( { id: ctrl.id } );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };

        this.moveTracksAndSetTags = function() {
            ctrl.release.$moveTracksAndSetTags( {}, function() {
                ctrl.release = Release.get( { id: ctrl.id } );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

    app.controller( 'ReleaseLinkWithMixxxController', [ 'Release', '$routeParams', '$location', function( Release, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.release = Release.get( { id: $routeParams.id }, function() {
            ctrl.linkedTracks = [];
            ctrl.notLinkedTracks = [];
            for ( var i = 0; i < ctrl.release.tracks.length; i++ ) {
                if ( ctrl.release.tracks[i].mixxxId === null ) {
                    ctrl.release.tracks[i].mixxxTrack = null;
                    ctrl.notLinkedTracks.push( ctrl.release.tracks[i] );
                } else {
                    ctrl.linkedTracks.push( ctrl.release.tracks[i] );
                }
            }
        });

        this.unlink = function( track ) {
            track.mixxxId = null;
        };

        this.submit = function() {
            for ( var i = 0; i < ctrl.notLinkedTracks.length; i++ ) {
                if ( ctrl.notLinkedTracks[i].mixxxTrack !== null ) {
                    ctrl.notLinkedTracks[i].mixxxId = ctrl.notLinkedTracks[i].mixxxTrack.id;
                    if ( ctrl.notLinkedTracks[i].location === null || ctrl.notLinkedTracks[i].location === "" ) {
                        ctrl.notLinkedTracks[i].location = ctrl.notLinkedTracks[i].mixxxTrack.location.location;
                    }
                }
            }
            ctrl.release.$update( {}, function() {
                $location.path( '/releases/' + ctrl.id );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
        
    }]);

    app.controller( 'ReleaseSetCoverController', [ 'Release', 'BeatportRelease', 'DiscogsRelease', '$routeParams', '$location', function( Release, BeatportRelease, DiscogsRelease, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.release = Release.get( { id: $routeParams.id }, function() {
            if ( ctrl.release.beatportId !== null ) {
                ctrl.beatportRelease = BeatportRelease.get( { id: ctrl.release.beatportId } );
            }
            if ( ctrl.release.discogsId !== null ) {
                ctrl.discogsRelease = DiscogsRelease.get( { id: ctrl.release.discogsId } );
            }
        });

        this.setCover = function( url ) {
            ctrl.release.$setCover( { url: url }, function() {
                $location.path( '/releases/' + ctrl.id );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };

        this.moveCover = function() {
            ctrl.release.$moveCover( {}, function() {
                $location.path( '/releases/' + ctrl.id );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
    }]);

    app.controller( 'ReleaseLinkDownloadsController', [ 'Release', '$routeParams', '$location', function( Release, $routeParams, $location ) {
        var ctrl = this;
        this.id = $routeParams.id;
        this.release = Release.get( { id: $routeParams.id } );

        this.submit = function() {
            for ( var i = 0; i < ctrl.release.tracks.length; i++ ) {
                if ( ctrl.release.tracks[ i ].download !== undefined  &&
                    ctrl.release.tracks[ i ].download !== null ) {
                    ctrl.release.tracks[ i ].location = ctrl.release.tracks[ i ].download.absolutePath;
                }
            }
            ctrl.release.$update( {}, function() {
                $location.path( '/releases/' + ctrl.id );
            }, function( httpResponse ) {
                alert( 'Error ' + httpResponse );
            });
        };
        
    }]);

    app.controller( 'KeysController', function() {
        this.keys = [
            { id: 1, name: "1A G#m" },
            { id: 2, name: "2A D#m" },
            { id: 3, name: "3A A#m" },
            { id: 4, name: "4A Fm" },
            { id: 5, name: "5A Cm" },
            { id: 6, name: "6A Gm" },
            { id: 7, name: "7A Dm" },
            { id: 8, name: "8A Am" },
            { id: 9, name: "9A Em" },
            { id: 10, name: "10A Bm" },
            { id: 11, name: "11A F#m" },
            { id: 12, name: "12A C#m" },
            { id: 13, name: "1B B" },
            { id: 14, name: "2B F#" },
            { id: 15, name: "3B C#" },
            { id: 16, name: "4B G#" },
            { id: 17, name: "5B D#" },
            { id: 18, name: "6B A#" },
            { id: 19, name: "7B F" },
            { id: 20, name: "8B C" },
            { id: 21, name: "9B G" },
            { id: 22, name: "10B D" },
            { id: 23, name: "11B A" },
            { id: 24, name: "12B E" }
        ];
    });

})();
